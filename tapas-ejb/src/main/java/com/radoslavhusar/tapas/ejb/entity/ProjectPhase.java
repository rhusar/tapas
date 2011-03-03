package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "PROJECT_PHASE")
public class ProjectPhase implements Serializable, Comparable {

   private static final long serialVersionUID = 3L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column
   private String name;
   @Column
   @Temporal(javax.persistence.TemporalType.DATE)
   private Date startDate;
   @Column
   @Temporal(javax.persistence.TemporalType.DATE)
   private Date endDate;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Date getEndDate() {
      return endDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   public Date getStartDate() {
      return startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   /**
    * Sorts by start date.
    *
    * @return positive integer if this phase starts after the compared to.
    */
   @Override
   public int compareTo(Object o) {
      if (o instanceof ProjectPhase) {
         if (((ProjectPhase) o).getStartDate().after(this.startDate)) {
            return 1;
         } else {
            return -1;
         }
      }

      return 0;
   }
}
