package com.radoslavhusar.tapas.ejb.solver;

import com.radoslavhusar.tapas.ejb.session.ResourceFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.TaskFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import org.drools.planner.config.XmlSolverConfigurer;
import org.drools.planner.core.Solver;
import org.drools.planner.core.solution.Solution;

@Stateless
@Local(SolverFacadeLocal.class)
public class SolverFacade implements SolverFacadeLocal {

   @EJB
   private TaskFacadeLocal tasksBean;
   @EJB
   private ResourceFacadeLocal resourcesBean;

   @Override
   public Solution solveAssignments(long projectId) {
      XmlSolverConfigurer solverConfigurer = new XmlSolverConfigurer();
      solverConfigurer.configure("/com/radoslavhusar/tapas/ejb/solver/taskAllocationSolverConfig.xml");
      Solver solver = solverConfigurer.buildSolver();

      // Starting solution
      TaskAllocationSolution tas = new TaskAllocationSolution(tasksBean.findAllForProject(projectId), resourcesBean.findAllForProject(projectId));

      solver.setStartingSolution(tas);
      solver.solve();
      return solver.getBestSolution();
   }
}
