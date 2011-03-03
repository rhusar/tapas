package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE_PROJECT_ALLOCATION")
public class ResourceProjectAllocation implements Serializable {

   private static final long serialVersionUID = 2L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @ManyToOne
   private Resource resource;
   @ManyToOne
   private Project project;
   // In spread called "Availability Rate"
   @Column
   private Byte percent;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public Byte getPercent() {
      return percent;
   }

   public void setPercent(Byte percent) {
      this.percent = percent;
   }

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }

   public Resource getResource() {
      return resource;
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }
}
