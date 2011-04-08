package com.radoslavhusar.tapas.ejb.stats;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import java.io.Serializable;

/**
 * Used in Hibernate QL, edit with caution! 
 * Parameters need to be synced with the queries.
 */
public class ResourcePhaseStatsEntry implements Serializable {

   private Resource resource;
   private double allocated;
   private double completed;
   private double rate;

   /**
    * Empty constructor needed by GWT-Gilead! and others.
    * E.g.: http://forums.smartclient.com/showthread.php?t=4966
    */
   public ResourcePhaseStatsEntry() {
   }

   /** 
    * Used for unassigned tasks.
    * 
    * @param allocated
    * @param completed 
    */
   public ResourcePhaseStatsEntry(double allocated, double completed) {
      this.resource = null;
      this.rate = 0;
      this.allocated = allocated;
      this.completed = completed;
   }

   /**
    * Cannot be simple types because of Hibernate.
    * 
    * @param resource
    * @param allocated
    * @param completed
    * @param rate 
    */
   public ResourcePhaseStatsEntry(Resource resource, Double allocated, Double completed, Double rate) {
      this.resource = resource;
      this.allocated = allocated == null ? 0 : allocated;
      this.completed = completed == null ? 0 : completed;
      this.rate = rate == null ? 0 : rate;
   }

   // Derived
   public double getRemaining() {
      return allocated - completed;
   }

   /**
    * @return raw number of days to complete the task (i.e. no calendar data)
    */
   public double getRawDaysToComplete() {
      return getRemaining() / rate;
   }

   // Get+Set
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

   public Resource getResource() {
      return resource;
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }

   public double getRate() {
      return rate;
   }

   public void setRate(double rate) {
      this.rate = rate;
   }

   @Override
   public String toString() {
      return "ResourceStats{resource=" + resource + ", allocated=" + allocated + ", completed=" + completed + ", rate=" + rate + '}';
   }
}
