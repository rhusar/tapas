package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE")
public class Resource implements Serializable {

   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column
   private String name;
   @Column
   private byte contract;
   @ManyToOne
   private ResourceGroup group;
   @OneToMany(fetch = FetchType.LAZY)
   private List<ResourceProjectAllocation> resourceProjectAllocations;

   public ResourceGroup getGroup() {
      return group;
   }

   public void setGroup(ResourceGroup group) {
      this.group = group;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public byte getContract() {
      return contract;
   }

   public void setContract(byte contact) {
      this.contract = contact;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<ResourceProjectAllocation> getResourceProjectAllocations() {
      return resourceProjectAllocations;
   }

   public void setResourceProjectAllocations(List<ResourceProjectAllocation> resourceProjectAllocations) {
      this.resourceProjectAllocations = resourceProjectAllocations;
   }
}
