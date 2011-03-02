package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

   private static final long serialVersionUID = 1L;
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
   private Date targetDate;
   @OneToMany(fetch = FetchType.EAGER)
   private List<ProjectPhase> phases;

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

   public Date getStartDate() {
      return startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   public Date getTargetDate() {
      return targetDate;
   }

   public void setTargetDate(Date targetDate) {
      this.targetDate = targetDate;
   }

   @SuppressWarnings("ReturnOfCollectionOrArrayField")
   public List<ProjectPhase> getPhases() {
      return phases;
   }

   public void setPhases(List<ProjectPhase> phases) {
      this.phases = phases;
   }
}
