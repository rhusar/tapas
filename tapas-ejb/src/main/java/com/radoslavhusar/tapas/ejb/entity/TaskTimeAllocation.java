package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TASK_TIME_ALLOCATION")
public class TaskTimeAllocation implements Serializable {

   private static final long serialVersionUID = 1L;
   @Id
   @Column
   private long id;
   @Column
   private double timeAllocation;
   @Column
   private double timeCompleted;
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   private Task task;
   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   private ProjectPhase phase;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
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

   public double getTimeAllocation() {
      return timeAllocation;
   }

   public void setTimeAllocation(double timeAllocation) {
      this.timeAllocation = timeAllocation;
   }

   public double getTimeCompleted() {
      return timeCompleted;
   }

   public void setTimeCompleted(double timeCompleted) {
      this.timeCompleted = timeCompleted;
   }
}
