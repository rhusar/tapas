package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long projectId;

   public Long getProjectId() {
      return projectId;
   }

   public void setProjectId(Long projectId) {
      this.projectId = projectId;
   }
}
