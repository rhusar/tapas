package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE_ALLOCATION")
public class ResourceAllocation implements Serializable {

   private static final long serialVersionUID = 3L;
   @EmbeddedId
   private ResourceAllocationPK key;
   @Column
   private Byte percent; // In spreadsheet called "Availability Rate"

   public ResourceAllocationPK getKey() {
      return key;
   }

   public void setKey(ResourceAllocationPK key) {
      this.key = key;
   }

   public Byte getPercent() {
      return percent;
   }

   public void setPercent(Byte percent) {
      this.percent = percent;
   }

   @Override
   public String toString() {
      return "ResourceAllocation{id=(resource=" + key == null ? null : key.getResource().getId() + ",project=" + key == null ? null : key.getProject().getId() + "),percent=" + percent + '}';
   }
}
