package com.radoslavhusar.tapas.ejb.solver;

import org.drools.planner.core.localsearch.LocalSearchSolverScope;
import org.drools.planner.core.solution.initializer.StartingSolutionInitializer;

public class TaskAllocationSolutionInitializer implements StartingSolutionInitializer {

   private boolean initialized = false;

   @Override
   public boolean isSolutionInitialized(LocalSearchSolverScope arg0) {
      return this.initialized;
   }

   @Override
   public void initializeSolution(LocalSearchSolverScope arg0) {
      
      
      this.initialized = true;
   }
}
