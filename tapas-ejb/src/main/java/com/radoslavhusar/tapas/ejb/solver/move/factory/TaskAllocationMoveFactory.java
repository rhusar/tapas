package com.radoslavhusar.tapas.ejb.solver.move.factory;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.solver.TaskAllocationSolution;
import com.radoslavhusar.tapas.ejb.solver.move.NextResourceMove;
import java.util.ArrayList;
import java.util.List;
import org.drools.planner.core.move.Move;
import org.drools.planner.core.move.factory.CachedMoveFactory;
import org.drools.planner.core.solution.Solution;

public class TaskAllocationMoveFactory extends CachedMoveFactory {

   @Override
   public List<Move> createCachedMoveList(Solution solution) {
      List<Move> moveList = new ArrayList<Move>();

      TaskAllocationSolution tas = (TaskAllocationSolution) solution;
      for (Task t : tas.getTasks()) {
         for (Resource r : tas.getResources()) {
            Move move = new NextResourceMove(t, r);
            moveList.add(move);
         }
      }

      return moveList;
   }
}
