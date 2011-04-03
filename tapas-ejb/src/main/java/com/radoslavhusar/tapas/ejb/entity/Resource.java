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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Fetching options: http://docs.jboss.org/hibernate/entitymanager/3.5/reference/en/html/queryhql.html
 * 
 */
@Entity
@Table(name = "RESOURCE")
@NamedQuery(name = "resourcesForProject", query = "select object(o) from Resource as o fetch all properties inner join o.resourceAllocations as a  where a.key.project.id = :projectid")
public class Resource implements Serializable {

   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @Column
   private String name;
   @Column
   private byte contract;
   @ManyToOne(fetch = FetchType.EAGER)
   private ResourceGroup group;
   @OneToMany(mappedBy = "key.resource", fetch = FetchType.LAZY)
   private List<ResourceAllocation> resourceAllocations;
   @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
   private List<Task> tasks;
   // Owning relation! Must be careful.
   @OneToMany(fetch = FetchType.LAZY)
   private List<Trait> traits;

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

   public List<ResourceAllocation> getResourceAllocations() {
      return resourceAllocations;
   }

   public void setResourceAllocations(List<ResourceAllocation> resourceAllocations) {
      this.resourceAllocations = resourceAllocations;
   }

   public List<Task> getTasks() {
      return tasks;
   }

   public void setTasks(List<Task> tasks) {
      this.tasks = tasks;
   }

   public List<Trait> getTraits() {
      return traits;
   }

   public void setTraits(List<Trait> traits) {
      this.traits = traits;
   }

   @Override
   public String toString() {
      return "Resource{id=" + id
              + ",name=" + name
              + ",contract=" + contract + "}";
   }
}
