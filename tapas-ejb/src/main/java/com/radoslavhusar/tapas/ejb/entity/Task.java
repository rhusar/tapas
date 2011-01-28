package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TASK")
public class Task implements Serializable {

   private static final long serialVersionUID = 7440297955003302414L;
   @Id
   @Column
   private long taskId;

   public long getTaskId() {
      return taskId;
   }

   public void setTaskId(long taskId) {
      this.taskId = taskId;
   }
}
