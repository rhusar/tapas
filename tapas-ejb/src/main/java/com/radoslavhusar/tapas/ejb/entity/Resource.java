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
   private Long id;
   @Column
   private String name;
   @Column
   private Byte contract;
   @ManyToOne
   private ResourceGroup group;
   @OneToMany(mappedBy = "key.resource", fetch = FetchType.LAZY)
   private List<ResourceAllocation> allocations;
   @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
   private List<Task> tasks;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public ResourceGroup getGroup() {
      return group;
   }

   public void setGroup(ResourceGroup group) {
      this.group = group;
   }

   public Byte getContract() {
      return contract;
   }

   public void setContract(Byte contact) {
      this.contract = contact;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<ResourceAllocation> getAllocations() {
      return allocations;
   }

   public void setAllocations(List<ResourceAllocation> allocations) {
      this.allocations = allocations;
   }

   public List<Task> getTasks() {
      return tasks;
   }

   public void setTasks(List<Task> tasks) {
      this.tasks = tasks;
   }

   @Override
   public String toString() {
      return "Resource{id=" + id + ", name=" + name + ", contract=" + contract + '}';
   }
}
