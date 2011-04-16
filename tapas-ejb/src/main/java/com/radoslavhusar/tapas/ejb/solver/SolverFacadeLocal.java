package com.radoslavhusar.tapas.ejb.solver;

import com.radoslavhusar.tapas.ejb.stats.TaskAllocationPlanMeta;

public interface SolverFacadeLocal {

   TaskAllocationPlanMeta solveAssignments(long projectId);
}
