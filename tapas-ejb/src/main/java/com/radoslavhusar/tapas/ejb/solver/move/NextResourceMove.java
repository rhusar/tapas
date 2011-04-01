package com.radoslavhusar.tapas.ejb.solver.move;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.Task;
import org.drools.FactHandle;
import org.drools.WorkingMemory;
import org.drools.planner.core.move.Move;

public class NextResourceMove implements Move {

   private final Task task;
   private final Resource resource;

   public NextResourceMove(Task task, Resource resource) {
      this.task = task;
      this.resource = resource;
   }

   @Override
   public boolean isMoveDoable(WorkingMemory wm) {
      if (task.getResource().equals(resource)) {
         return false;
      }

      return true;
   }

   @Override
   public Move createUndoMove(WorkingMemory wm) {
      Move undoMove = new NextResourceMove(task, task.getResource());
      return undoMove;
   }

   @Override
   public void doMove(WorkingMemory wm) {
      FactHandle taskHandle = wm.getFactHandle(task);
      task.setResource(resource);
      wm.update(taskHandle, task);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final NextResourceMove other = (NextResourceMove) obj;
      if (this.task != other.task && (this.task == null || !this.task.equals(other.task))) {
         return false;
      }
      if (this.resource != other.resource && (this.resource == null || !this.resource.equals(other.resource))) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 41 * hash + (this.task != null ? this.task.hashCode() : 0);
      hash = 41 * hash + (this.resource != null ? this.resource.hashCode() : 0);
      return hash;
   }

   @Override
   public String toString() {
      return task.getId() + " => " + resource.getId();
   }
}
