package com.radoslavhusar.tapas.ejb.stats;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProjectStats implements Serializable {

   private long projectId;
   private double completed;
   private double allocated;
   // Public structure actually
   // TODO: how to sort?
   private Map<Long, ProjectPhaseStats> projection = new HashMap<Long, ProjectPhaseStats>();
   private double mandayRate;

   /**
    * Empty constructor needed by GWT-Gilead!
    * E.g.: http://forums.smartclient.com/showthread.php?t=4966
    */
   public ProjectStats() {
   }

   public ProjectStats(long projectId) {
      this.projectId = projectId;
   }

   public Map<Long, ProjectPhaseStats> getProjection() {
      return projection;
   }

   /* public void setProjectionEntry(Long phaseId, ProjectPhaseStats data) {
   projection.put(phaseId, data);
   }*/
   public long getProjectId() {
      return projectId;
   }

   public void setProjectId(long projectId) {
      this.projectId = projectId;
   }

   public double getAllocated() {
      return allocated;
   }

   public void setAllocated(double allocated) {
      this.allocated = allocated;
   }

   public double getCompleted() {
      return completed;
   }

   public void setCompleted(double completed) {
      this.completed = completed;
   }

   public double getRemaining() {
      return allocated - completed;
   }

   public double getMandayRate() {
      return mandayRate;
   }

   public void setMandayRate(double mandayRate) {
      this.mandayRate = mandayRate;
   }

   @Override
   public String toString() {
      return "ProjectStats{" + "projectId=" + projectId + ", completed=" + completed + ", allocated=" + allocated + ", projection=" + projection + ", mandayRate=" + mandayRate + '}';
   }
}
