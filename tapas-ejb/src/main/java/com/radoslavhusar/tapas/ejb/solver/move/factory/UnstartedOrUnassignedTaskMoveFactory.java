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

/**
 * This Move Factory generates only moves for UNASSIGNED or UNSTARTED tasks. 
 * So either the person hasn't started so its OK to "steal" or the tasks it 
 * not assigned at all. This is the default factory.
 * 
 * @author <a href="http://www.radoslavhusar.com/">Radoslav Husar</a>
 */
public class UnstartedOrUnassignedTaskMoveFactory extends CachedMoveFactory {

   @Override
   public List<Move> createCachedMoveList(Solution solution) {
      List<Move> moveList = new ArrayList<Move>();

      TaskAllocationSolution tas = (TaskAllocationSolution) solution;

      for (Task task : tas.getTasks()) {

         // Is task already assigned?
         if (task.getResource() != null && task.getDroolsCompleted() > 0) {
            // So don't add moves.
            continue;
         }

         for (Resource r : tas.getResources()) {
            Move move = new NextResourceMove(task, r);
            moveList.add(move);
         }
      }

      return moveList;
   }
}
