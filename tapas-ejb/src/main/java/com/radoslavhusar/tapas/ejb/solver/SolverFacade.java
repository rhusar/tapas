package com.radoslavhusar.tapas.ejb.solver;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import com.radoslavhusar.tapas.ejb.session.ResourceFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.TaskFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.drools.planner.config.XmlSolverConfigurer;
import org.drools.planner.core.Solver;
import org.drools.planner.core.solution.Solution;
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
   public Solution solveAssignments(long projectId) {
      XmlSolverConfigurer solverConfigurer = new XmlSolverConfigurer();

      // Get from resource
      solverConfigurer.configure("/com/radoslavhusar/tapas/ejb/solver/taskAllocationSolverConfig.xml");

      Solver solver = solverConfigurer.buildSolver();


      List<Resource> detachedResources = resourcesBean.findAllForProject(projectId);
      for (Resource t : detachedResources) {
         detach(t);
         //t.setTasks(new ArrayList<Task>());
      }

      // Starting solution
      List<Task> detachedTasks = tasksBean.findAllForProject(projectId);
      for (Task t : detachedTasks) {

         double sum = 0;
         for (TimeAllocation ta : t.getTimeAllocations()) {
            sum += ta.getAllocation();
            sum -= ta.getCompleted();
         }
         t.setRemaining(sum);

         t.setResource(detachedResources.get(0));
         
         detach(t);
      }



      /*for (Task t : detachedTasks) {
      // Lets assign all tasks to the first guy
      t.setResource(detachedResources.get(0));
      detachedResources.get(0).getTasks().add(t);
      }*/

      TaskAllocationSolution tas = new TaskAllocationSolution(detachedTasks, detachedResources);

      solver.setStartingSolution(tas);

      // This method exits when a solution is ready.
      solver.solve();

      return solver.getBestSolution();
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
