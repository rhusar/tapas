package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name = "TASK")
@NamedQuery(name = "tasksForProject", query = "select object(o) from Task as o where o.project.id = :projectid")
public class Task implements Serializable {

   private static final long serialVersionUID = 7440293452003302414L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @Version
   private long version;
   @Column
   private String unifiedId;
   @Lob
   @Column(length = 2048)
   private String name;
   /*@Column
   private String summary;*/
   @ManyToOne(fetch = FetchType.EAGER)
   private Resource resource;
   @ManyToOne(fetch = FetchType.EAGER)
   private ResourceGroup resourceGroup;
   @Column
   private Byte priority;
   @ManyToOne(fetch = FetchType.LAZY, optional = false) // Fetch lazilly, we have typically the instance already looked up.
   private Project project;
   @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
   private List<TimeAllocation> timeAllocations;
   @Column
   private TaskStatus status;
   @ManyToOne(fetch = FetchType.EAGER)
   private Trait requiredTrait;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public long getVersion() {
      return version;
   }

   public void setVersion(long version) {
      this.version = version;
   }

   public String getUnifiedId() {
      return unifiedId;
   }

   public void setUnifiedId(String unifiedId) {
      this.unifiedId = unifiedId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Byte getPriority() {
      return priority;
   }

   public void setPriority(Byte priority) {
      this.priority = priority;
   }

   public TaskStatus getStatus() {
      return status;
   }

   public void setStatus(TaskStatus status) {
      this.status = status;
   }

   /*public String getSummary() {
   return summary;
   }
   
   public void setSummary(String summary) {
   this.summary = summary;
   }*/
   @SuppressWarnings("ReturnOfCollectionOrArrayField")
   public List<TimeAllocation> getTimeAllocations() {
      return timeAllocations;
   }

   public void setTimeAllocations(List<TimeAllocation> timeAllocations) {
      this.timeAllocations = timeAllocations;
   }

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }

   public Resource getResource() {
      return resource;
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }

   public ResourceGroup getResourceGroup() {
      return resourceGroup;
   }

   public void setResourceGroup(ResourceGroup resourceGroup) {
      this.resourceGroup = resourceGroup;
   }

   public Trait getRequiredTrait() {
      return requiredTrait;
   }

   public void setRequiredTrait(Trait requiredTrait) {
      this.requiredTrait = requiredTrait;
   }

   @Override
   public String toString() {
      return "Task{id=" + id
              + ",uid=" + unifiedId
              + ",name=" + name
              + ",assignee=" + (resource == null ? "null" : resource.getName())
              + "}";
   }

   public static String formatState(TaskStatus state) {
      return state == null ? "" : state.toString().substring(0, 1).toUpperCase() + state.toString().substring(1).toLowerCase();
   }

   /**
    * Needed for Drools Planner. There must be a better way, this sucks.
    * Time allocation and project are NOT cloned!
    * 
    * This cant really be done by implementing interface Cloneable because GWT compiler has problem with it for this reason: 
    * [INFO][ERROR] Line 143: The method clone() of type Task must override or implement a supertype method
    * 
    * @return a clone
    */
   public Task droolsClone() {
      Task clone = new Task();
      clone.id = id;
      clone.unifiedId = unifiedId;
      clone.name = name;
      //clone.summary = summary;
      // Allocations are not cloned.
      clone.project = project;
      clone.resource = resource;
      clone.resourceGroup = resourceGroup;
      clone.priority = priority;
      // Isnt there a better way to copy over Collections?
      clone.timeAllocations = new ArrayList(timeAllocations.size());
      // Allocations are not cloned.
      clone.timeAllocations.addAll(timeAllocations);
      clone.status = status;
      return clone;
   }
   @Transient
   private double droolsAllocated;
   @Transient
   private double droolsCompleted;

   public double getDroolsAllocated() {
      return droolsAllocated;
   }

   public void setDroolsAllocated(Double droolsAllocated) {
      this.droolsAllocated = droolsAllocated == null ? 0 : droolsAllocated;
   }

   public double getDroolsCompleted() {
      return droolsCompleted;
   }

   public void setDroolsCompleted(Double droolsCompleted) {
      this.droolsCompleted = droolsCompleted == null ? 0 : droolsCompleted;
   }

   /*
   // Could be probably removed.
   @Override
   public boolean equals(Object obj) {
   if (obj == null) {
   return false;
   }
   if (getClass() != obj.getClass()) {
   return false; // Only compares IDs!
   }
   final Task other = (Task) obj;
   if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
   return false;
   }
   return true;
   }
   // Could be probably removed.
   @Override
   public int hashCode() {
   int hash = 3;
   hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
   return hash;
   }
    */
}
