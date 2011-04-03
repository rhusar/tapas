package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE_ALLOCATION")
@NamedQuery(name = "resourceAllocationsForProject", query = "select object(o) from ResourceAllocation as o where o.key.project.id = :projectid")
public class ResourceAllocation implements Serializable {

   private static final long serialVersionUID = 3L;
   @EmbeddedId
   private ResourceAllocationPK key;
   @Column // In spreadsheet called "Availability Rate"
   private Byte percent; // nullable value makes sense for lurking resources

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
      return "ResourceAllocation{id=(resource=" + (key == null ? "null" : key.getResource().getId())
              + ",project=" + (key == null ? "null" : key.getProject().getId())
              + "),percent=" + percent
              + "}";
   }
}
