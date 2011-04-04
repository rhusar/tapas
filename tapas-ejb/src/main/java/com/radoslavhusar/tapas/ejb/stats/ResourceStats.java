package com.radoslavhusar.tapas.ejb.stats;

import com.radoslavhusar.tapas.ejb.entity.Resource;

public class ResourceStats {

   private Resource resource;
   private Double assigned;
   private Double completed;
   private Double rate;

   public ResourceStats(Resource resource, Double assigned, Double completed, Double rate) {
      this.resource = resource;
      this.assigned = assigned;
      this.completed = completed;
      this.rate = rate;
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

   public Double getRemaining() {
      return (assigned == null || completed == null) ? null : (assigned - completed);
   }

   public Resource getResource() {
      return resource;
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }

   public Double getRate() {
      return rate;
   }

   public void setRate(Double rate) {
      this.rate = rate;
   }

   @Override
   public String toString() {
      return "ResourceStats{resource=" + resource + ", assigned=" + assigned + ", completed=" + completed + ", rate=" + rate + '}';
   }
}
