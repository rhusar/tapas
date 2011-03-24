package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;

public class ResourceAllocationData implements Serializable {

   private long resourceId;
   private long projectId;
   private double p1Allocation;
   private double p1Completed;
   private double p2Allocation;
   private double p2Completed;
   private double p3Allocation;
   private double p3Completed;
   private double tbdAllocation;
   private double tbdCompleted;

   public ResourceAllocationData() {
   }

   public ResourceAllocationData(long resourceId, long projectId) {
      this.resourceId = resourceId;
      this.projectId = projectId;
   }

   public ResourceAllocationData(long resourceId, long projectId, double p1Allocation, double p1Completed, double p2Allocation, double p2Completed, double p3Allocation, double p3Completed, double tbdAllocation, double tbdCompleted) {
      this.resourceId = resourceId;
      this.projectId = projectId;
      this.p1Allocation = p1Allocation;
      this.p1Completed = p1Completed;
      this.p2Allocation = p2Allocation;
      this.p2Completed = p2Completed;
      this.p3Allocation = p3Allocation;
      this.p3Completed = p3Completed;
      this.tbdAllocation = tbdAllocation;
      this.tbdCompleted = tbdCompleted;
   }

   public long getProjectId() {
      return projectId;
   }

   public void setProjectId(long projectId) {
      this.projectId = projectId;
   }

   public long getResourceId() {
      return resourceId;
   }

   public void setResourceId(long resourceId) {
      this.resourceId = resourceId;
   }

   public double getP1Allocation() {
      return p1Allocation;
   }

   public void setP1Allocation(double p1Allocation) {
      this.p1Allocation = p1Allocation;
   }

   public double getP1Completed() {
      return p1Completed;
   }

   public void setP1Completed(double p1Completed) {
      this.p1Completed = p1Completed;
   }

   public double getP2Allocation() {
      return p2Allocation;
   }

   public void setP2Allocation(double p2Allocation) {
      this.p2Allocation = p2Allocation;
   }

   public double getP2Completed() {
      return p2Completed;
   }

   public void setP2Completed(double p2Completed) {
      this.p2Completed = p2Completed;
   }

   public double getP3Allocation() {
      return p3Allocation;
   }

   public void setP3Allocation(double p3Allocation) {
      this.p3Allocation = p3Allocation;
   }

   public double getP3Completed() {
      return p3Completed;
   }

   public void setP3Completed(double p3Completed) {
      this.p3Completed = p3Completed;
   }

   public double getTbdAllocation() {
      return tbdAllocation;
   }

   public void setTbdAllocation(double tbdAllocation) {
      this.tbdAllocation = tbdAllocation;
   }

   public double getTbdCompleted() {
      return tbdCompleted;
   }

   public void setTbdCompleted(double tbdCompleted) {
      this.tbdCompleted = tbdCompleted;
   }

   @Override
   public String toString() {
      return "ResourceAllocationData{resourceId=" + resourceId + ", projectId=" + projectId + ", p1Allocation=" + p1Allocation + ", p1Completed=" + p1Completed + ", p2Allocation=" + p2Allocation + ", p2Completed=" + p2Completed + ", p3Allocation=" + p3Allocation + ", p3Completed=" + p3Completed + ", tbdAllocation=" + tbdAllocation + ", tbdCompleted=" + tbdCompleted + "}";
   }
}
