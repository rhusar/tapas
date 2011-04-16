package com.radoslavhusar.tapas.ejb.solver;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import com.radoslavhusar.tapas.ejb.session.ResourceFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.TaskFacadeLocal;
import com.radoslavhusar.tapas.ejb.stats.TaskAllocationPlanMeta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.drools.planner.config.XmlSolverConfigurer;
import org.drools.planner.core.Solver;
import org.hibernate.Session;

@Stateless
@Local(SolverFacadeLocal.class)
public class SolverFacade implements SolverFacadeLocal {

   @EJB
   private TaskFacadeLocal tasksBean;
   @EJB
   private ResourceFacadeLocal resourcesBean;
   @PersistenceContext
   private EntityManager em;

   @Override
   public TaskAllocationPlanMeta solveAssignments(long projectId) {
      XmlSolverConfigurer solverConfigurer = new XmlSolverConfigurer();

      // Get from resource
      solverConfigurer.configure("/com/radoslavhusar/tapas/ejb/solver/taskAllocationSolverConfig.xml");

      // Get config
      //((LocalSearchSolverConfig) solverConfigurer.getConfig()).getSelectorConfig().setMoveFactoryClass(null);

      Solver solver = solverConfigurer.buildSolver();


      List<Resource> detachedResources = resourcesBean.findAllForProject(projectId);
      for (Resource t : detachedResources) {
         detach(t);
         //t.setTasks(new ArrayList<Task>());
      }

      // Starting solution
      List<Task> detachedTasks = tasksBean.findAllForProject(projectId);
      for (Task t : detachedTasks) {

         double allocated = 0;
         double completed = 0;
         for (TimeAllocation ta : t.getTimeAllocations()) {
            allocated += ta.getAllocated();
            completed -= ta.getCompleted();
         }
         t.setDroolsAllocated(allocated);
         t.setDroolsCompleted(completed);

         // Debug, avoid NPE problems:
         // t.setResource(detachedResources.get(0));

         detach(t);
      }

      TaskAllocationSolution tas = new TaskAllocationSolution(detachedTasks, detachedResources);

      solver.setStartingSolution(tas);


      // This method exits when a solution is ready.
      solver.solve();

      // Feed it to a DTO to get the data to the client.
      TaskAllocationSolution best = (TaskAllocationSolution) solver.getBestSolution();
      Map<Long, Long> taskToResource = new HashMap<Long, Long>();

      for (Task t : best.getTasks()) {
         taskToResource.put(t.getId(), t.getResource() == null ? null : t.getResource().getId());
      }

      TaskAllocationPlanMeta meta = new TaskAllocationPlanMeta(projectId, taskToResource);
      return meta;
   }

   /**
    * Needs to be detached because Drools Planner modifies the objects.
    * 
    * See http://stackoverflow.com/questions/31446/detach-an-entity-from-jpa-ejb3-persistence-context
    * 
    * @param entity to be detached
    */
   private void detach(Object entity) {
      // Cast to hibernate session 
      Session session = (Session) em.getDelegate();
      // and evict it from context
      session.evict(entity);
   }
}
