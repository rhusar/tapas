package com.radoslavhusar.tapas.ejb.solver;

import org.drools.planner.core.solution.Solution;

public interface SolverFacadeLocal {

   Solution solveAssignments(long projectId);
}
