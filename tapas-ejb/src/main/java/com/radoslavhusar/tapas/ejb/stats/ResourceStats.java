package com.radoslavhusar.tapas.ejb.stats;

import com.radoslavhusar.tapas.ejb.entity.Resource;

/**
 * Used in Hibernate QL, edit with caution! 
 */
public class ResourceStats {

   private Resource resource;
   private double allocated;
   private double completed;
   private double rate;

   /**
    * Empty constructor needed by GWT-Gilead!
    * E.g.: http://forums.smartclient.com/showthread.php?t=4966
    */
   public ResourceStats() {
   }

   /**
    * Cannot be simple types because of Hibernate.
    * 
    * @param resource
    * @param allocated
    * @param completed
    * @param rate 
    */
   public ResourceStats(Resource resource, Double allocated, Double completed, Double rate) {
      this.resource = resource;
      this.allocated = allocated == null ? 0 : allocated;
      this.completed = completed == null ? 0 : completed;
      this.rate = rate == null ? 0 : rate;
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

   public Resource getResource() {
      return resource;
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }

   public double getRate() {
      return rate;
   }

   public void setRate(Double rate) {
      this.rate = rate;
   }

   @Override
   public String toString() {
      return "ResourceStats{resource=" + resource + ", allocated=" + allocated + ", completed=" + completed + ", rate=" + rate + '}';
   }
}
