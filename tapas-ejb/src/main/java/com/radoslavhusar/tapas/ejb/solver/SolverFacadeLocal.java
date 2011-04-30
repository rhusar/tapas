package com.radoslavhusar.tapas.ejb.solver;

import com.radoslavhusar.tapas.ejb.stats.TaskAllocationPlanMeta;
import com.radoslavhusar.tapas.ejb.stats.TaskAllocationPlanOptions;

public interface SolverFacadeLocal {

   /**
    * Uses default options.
    * 
    * @param projectId
    * @return 
    */
   TaskAllocationPlanMeta solveAssignments(long projectId);

   TaskAllocationPlanMeta solveAssignments(long projectId, TaskAllocationPlanOptions options);
}
