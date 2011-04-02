package com.radoslavhusar.tapas.ejb.solver;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.session.ResourceFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.TaskFacadeLocal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
      InputStream in = null;

      /* FIXME: Can be removed: Get from file?
      
      try {
      File file = new File("/home/rhusar/Dropbox/masthe/gilead/tapas-ejb/src/main/java/com/radoslavhusar/tapas/ejb/solver/taskAllocationSolverConfig2.xml");
      in = new FileInputStream(file);
      } catch (FileNotFoundException ex) {
      }
      
      solverConfigurer.configure(in); */

      // Get as resource
      solverConfigurer.configure("/com/radoslavhusar/tapas/ejb/solver/taskAllocationSolverConfig.xml");

      Solver solver = solverConfigurer.buildSolver();

      // Starting solution
      List<Task> detachedTasks = tasksBean.findAllForProject(projectId);
      for (Task t : detachedTasks) {
         detach(t);
      }
      List<Resource> de = resourcesBean.findAllForProject(projectId);
      for (Resource t : de) {
         detach(t);
      }
      TaskAllocationSolution tas = new TaskAllocationSolution(detachedTasks, de);

      solver.setStartingSolution(tas);
      solver.solve();
      return solver.getBestSolution();
   }

   /**
    * Needs to be detached because Drools Planner modifies the objects.
    * 
    * http://stackoverflow.com/questions/31446/detach-an-entity-from-jpa-ejb3-persistence-context
    * 
    * @param entity to be detached
    */
   private void detach(Object entity) {
      Session session = (Session) em.getDelegate();
      session.evict(entity);
   }
}
