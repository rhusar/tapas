package com.radoslavhusar.tapas.ejb.stats;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProjectStats implements Serializable {

   private long projectId;
   private double completed;
   private double allocated;
   // Public structure actually
   private Map<Long, PhaseStatsEntry> projection = new HashMap<Long, PhaseStatsEntry>();
   /* For some stupid reason GILEAD doesn't serialize 2 maps in one entity. 
   private Map<Long, List<ResourceStats>> resources = new HashMap<Long, List<ResourceStats>>(); */
   private double mandayRate;

   /**
    * Empty constructor needed by GWT-Gilead!
    * E.g.: http://forums.smartclient.com/showthread.php?t=4966
    */
   public ProjectStats() {
   }

   public double getRemaining() {
      return allocated - completed;
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

   public double getMandayRate() {
      return mandayRate;
   }

   public void setMandayRate(double mandayRate) {
      this.mandayRate = mandayRate;
   }

   public long getProjectId() {
      return projectId;
   }

   public void setProjectId(long projectId) {
      this.projectId = projectId;
   }

   public Map<Long, PhaseStatsEntry> getProjection() {
      return projection;
   }

   public void setProjection(Map<Long, PhaseStatsEntry> projection) {
      this.projection = projection;
   }

   @Override
   public String toString() {
      return "ProjectStats{" + "projectId=" + projectId + ", completed=" + completed + ", allocated=" + allocated + ", projection=" + projection + ", mandayRate=" + mandayRate + '}';
   }
}
