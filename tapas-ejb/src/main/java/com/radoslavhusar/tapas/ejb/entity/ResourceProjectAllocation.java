package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE_PROJECT_ALLOCATION")
public class ResourceProjectAllocation implements Serializable {

   private static final long serialVersionUID = 3L;
   @EmbeddedId
   private ResourceProjectAllocationPK key;
   // In spread called "Availability Rate"
   @Column
   private Byte percent;

   public ResourceProjectAllocationPK getKey() {
      return key;
   }

   public void setKey(ResourceProjectAllocationPK key) {
      this.key = key;
   }

   public Byte getPercent() {
      return percent;
   }

   public void setPercent(Byte percent) {
      this.percent = percent;
   }
//   @Override
//   public String toString() {
//      return "ResourceProjectAllocation{id=" + (id == 0 ? "NEW" : id) + ",resource=" + resource.getId() + ",project=" + project.getId() + ",percent=" + percent + '}';
//   }
}
