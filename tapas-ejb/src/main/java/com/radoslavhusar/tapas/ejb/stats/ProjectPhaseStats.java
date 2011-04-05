package com.radoslavhusar.tapas.ejb.stats;

import java.io.Serializable;
import java.util.Date;

public class ProjectPhaseStats implements Serializable {

   private Date projectedEnd;
   private int slip;
   // ? private Date cummulativeEnd;
   private double completed;
   private double assigned;

   /**
    * Empty constructor needed by GWT-Gilead!
    * For instance http://forums.smartclient.com/showthread.php?t=4966
    */
   public ProjectPhaseStats() {
   }

   /**
    * Can use primitive types - used only from @{link:ProjectFacade#tallyProjectStats}.
    * @param assigned
    * @param completed
    * @param projectedEnd
    * @param slip 
    */
   public ProjectPhaseStats(double assigned, double completed, Date projectedEnd, int slip) {
      this.projectedEnd = projectedEnd;
      this.slip = slip;
      this.completed = completed;
      this.assigned = assigned;
   }

   public double getAssigned() {
      return assigned;
   }

   public void setAssigned(double assigned) {
      this.assigned = assigned;
   }

   public double getCompleted() {
      return completed;
   }

   public void setCompleted(double completed) {
      this.completed = completed;
   }

   public Date getProjectedEnd() {
      return projectedEnd;
   }

   public void setProjectedEnd(Date projectedEnd) {
      this.projectedEnd = projectedEnd;
   }

   public double getRemaining() {
      return assigned - completed;
   }

   public int getSlip() {
      return slip;
   }

   public void setSlip(int slip) {
      this.slip = slip;
   }

   @Override
   public String toString() {
      return "ProjectPhaseStats{" + "projectedEnd=" + projectedEnd == null ? "null" : projectedEnd
              + ", completed=" + completed + ", assigned=" + assigned + ",slip=" + slip + '}';
   }
}