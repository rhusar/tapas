package com.radoslavhusar.tapas.ejb.solver;

import javax.ejb.Local;
import javax.ejb.Stateless;
import org.drools.planner.config.XmlSolverConfigurer;
import org.drools.planner.core.Solver;
import org.drools.planner.core.solution.Solution;

@Stateless
@Local(SolverFacadeLocal.class)
public class SolverFacade implements SolverFacadeLocal {

   @Override
   public Solution solveAssignments(long projectId) {
      XmlSolverConfigurer configurer = new XmlSolverConfigurer();
      configurer.configure("/com/radoslavhusar/tapas/ejb/solver/taskAllocationSolverConfig.xml");
      Solver solver = configurer.buildSolver();
      solver.solve();
      return solver.getBestSolution();
   }
}
