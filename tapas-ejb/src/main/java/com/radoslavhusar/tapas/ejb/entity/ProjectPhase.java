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
public class ProjectPhase implements Serializable {

   private static final long serialVersionUID = 2L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @Column
   private String name;
   @Column
   @Temporal(javax.persistence.TemporalType.DATE)
   private Date startDate;
   @Column
   @Temporal(javax.persistence.TemporalType.DATE)
   private Date endDate;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
