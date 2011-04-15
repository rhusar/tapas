package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Fetching options: http://docs.jboss.org/hibernate/entitymanager/3.5/reference/en/html/queryhql.html
 * 
 */
@Entity
@Table(name = "RESOURCE")
@NamedQueries(value = {
   @NamedQuery(name = "resourcesForProject", query = "select object(o) from Resource as o fetch all properties inner join o.resourceAllocations as a where a.key.project.id = :projectid"),
   @NamedQuery(name = "resourcesNotOnProject", query = "select object(o) from Resource as o fetch all properties where o.id not in (select o.id from Resource as o inner join o.resourceAllocations as a where a.key.project.id = :projectid)")
})
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
   @ManyToMany(fetch = FetchType.LAZY)
   private Set<Trait> traits;

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

   public Set<Trait> getTraits() {
      return traits;
   }

   public void setTraits(Set<Trait> traits) {
      this.traits = traits;
   }

   /**
    * Use with ultra caution! Convenience method for Drools Planner occasions.
    * 
    * @return raw mandays per day
    */
   public double getRate() {
      return (double) resourceAllocations.get(0).getPercent() * (double) contract / (double) 10000;
   }

   // Could be removed.
   @Deprecated
   public Resource droolsClone() {
      Resource clone = new Resource();
      clone.id = id;
      clone.group = group;
      clone.name = name;
      clone.resourceAllocations = resourceAllocations;
      clone.traits = traits;
      clone.contract = contract;
      clone.tasks = new ArrayList();
      clone.tasks.addAll(tasks);
      return clone;
   }

   @Override
   public String toString() {
      return "Resource{id=" + id
              + ",name=" + name
              + ",contract=" + contract + "}";
   }

   // Could be probably removed.
   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Resource other = (Resource) obj;
      if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
         return false;
      }
      return true;
   }

   // Could be probably removed.
   @Override
   public int hashCode() {
      int hash = 7;
      hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
      return hash;
   }
}
