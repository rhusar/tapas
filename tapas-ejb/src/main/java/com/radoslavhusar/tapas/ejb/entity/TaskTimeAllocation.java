package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TASK_TIME_ALLOCATION")
public class TaskTimeAllocation implements Serializable {

   private static final long serialVersionUID = 1L;
   @Id
   @Column
   private long id;
   @Column
   private double allocation;

   public double getAllocation() {
      return allocation;
   }

   public void setAllocation(double allocation) {
      this.allocation = allocation;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }
}
