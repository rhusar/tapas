package com.radoslavhusar.tapas.ejb.stats;

import java.io.Serializable;
import java.util.Date;

public class ProjectPhaseStats implements Serializable {

   private Date projectedEnd;
   // ? private Date cummulativeEnd;
   private Double completed;
   private Double assigned;

   public ProjectPhaseStats(Double assigned, Double completed, Date projectedEnd) {
      this.projectedEnd = projectedEnd;
      this.completed = completed;
      this.assigned = assigned;
   }

   public Double getAssigned() {
      return assigned;
   }

   public void setAssigned(Double assigned) {
      this.assigned = assigned;
   }

   public Double getCompleted() {
      return completed;
   }

   public void setCompleted(Double completed) {
      this.completed = completed;
   }

   public Date getProjectedEnd() {
      return projectedEnd;
   }

   public void setProjectedEnd(Date projectedEnd) {
      this.projectedEnd = projectedEnd;
   }

   public Double getRemaining() {
      return assigned - completed;
   }
}