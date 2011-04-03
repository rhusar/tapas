package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TIME_ALLOCATION")
public class TimeAllocation implements Serializable {

   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   /* These are candidates for embedded IDs */
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   private Task task;
   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   private ProjectPhase phase;
   @Column
   private double allocation;
   @Column
   private double completed;
   @Column
   private String tracker;

   public TimeAllocation() {
   }

   public TimeAllocation(Task task, ProjectPhase phase) {
      this.task = task;
      this.phase = phase;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public double getAllocation() {
      return allocation;
   }

   public void setAllocation(double allocation) {
      this.allocation = allocation;
   }

   public double getCompleted() {
      return completed;
   }

   public void setCompleted(double completed) {
      this.completed = completed;
   }

   public ProjectPhase getPhase() {
      return phase;
   }

   public void setPhase(ProjectPhase phase) {
      this.phase = phase;
   }

   public Task getTask() {
      return task;
   }

   public void setTask(Task task) {
      this.task = task;
   }

   public String getTracker() {
      return tracker;
   }

   public void setTracker(String tracker) {
      this.tracker = tracker;
   }

   @Override
   public String toString() {
      return "TimeAllocation{id=" + id
              + ",allocation=" + allocation
              + ",completed=" + completed
              + ",task=" + (task == null ? "null" : task.getId())
              + ",phase=" + (phase == null ? "null" : phase.getId())
              + "}";
   }
}
