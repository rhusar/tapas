package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "TASK")
public class Task implements Serializable {

   private static final long serialVersionUID = 7440297955003302414L;
//   @Id
//   @Column
   private long taskId;
//   @Column //(nullable = false, length = 256)
   private String summary;
//   @Column
   private int someinteger;

   public Task() {
   }

   public Task(long taskId, String summary, int someinteger) {
      this.taskId = taskId;
      this.summary = summary;
      this.someinteger = someinteger;
   }

   public long getTaskId() {
      return taskId;
   }

   public void setTaskId(long taskId) {
      this.taskId = taskId;
   }

   public String getSummary() {
      return summary;
   }

   public void setSummary(String summary) {
      this.summary = summary;
   }

   public int getSomeinteger() {
      return someinteger;
   }

   public void setSomeinteger(int someinteger) {
      this.someinteger = someinteger;
   }
}
