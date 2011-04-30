package com.radoslavhusar.tapas.ejb.stats;

import java.io.Serializable;

/**
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
public class TaskAllocationPlanOptions implements Serializable {

   private boolean unstarted = false;
   private boolean unassigned = true;

   public TaskAllocationPlanOptions() {
   }

   public TaskAllocationPlanOptions(boolean unstarted, boolean unassigned) {
      this.unstarted = unstarted;
      this.unassigned = unassigned;
   }

   public boolean isUnassigned() {
      return unassigned;
   }

   public void setUnassigned(boolean unassigned) {
      this.unassigned = unassigned;
   }

   public boolean isUnstarted() {
      return unstarted;
   }

   public void setUnstarted(boolean unstarted) {
      this.unstarted = unstarted;
   }

   @Override
   public String toString() {
      return "TaskAllocationPlanOptions{unstarted=" + unstarted + ", unassigned=" + unassigned + '}';
   }
}
