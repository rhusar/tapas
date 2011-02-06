package com.radoslavhusar.tapas.ejb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE_GROUP")
public class ResourceGroup implements java.io.Serializable {

   private static final long serialVersionUID = 7440297955003302414L;
   @Id
   @Column
   private long resourceGroupId;

   public long getResourceGroupId() {
      return resourceGroupId;
   }

   public void setResourceGroupId(long resourceGroupId) {
      this.resourceGroupId = resourceGroupId;
   }
}
