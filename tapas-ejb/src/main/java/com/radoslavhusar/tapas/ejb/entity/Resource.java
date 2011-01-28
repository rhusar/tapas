package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE")
public class Resource implements Serializable {

   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long resourceId;

   public Long getResourceId() {
      return resourceId;
   }

   public void setResourceId(Long resourceId) {
      this.resourceId = resourceId;
   }
}
