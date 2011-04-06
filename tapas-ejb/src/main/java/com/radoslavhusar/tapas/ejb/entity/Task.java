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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
   @Column
   private String name;
   @Column
   private String summary;
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

   public String getSummary() {
      return summary;
   }

   public void setSummary(String summary) {
      this.summary = summary;
   }

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
              + ",assignee=" + (resource == null ? null : resource.getName()) + "}";
   }

   public static String formatState(TaskStatus state) {
      return state == null ? "" : state.toString().substring(0, 1).toUpperCase() + state.toString().substring(1).toLowerCase();
   }

   /**
    * Needed for drools planner. There must be a better way, this sucks.
    * 
    * This cant really be done by implementing interface Cloneable because GWT compiler has problem with it:
    * 
    * [INFO]          [ERROR] Line 143: The method clone() of type Task must override or implement a supertype method
    * 
    * @return a clone
    */
   public Task droolsClone() {
      Task clone = new Task();
      clone.id = id;
      clone.unifiedId = unifiedId;
      clone.name = name;
      clone.summary = summary;
      clone.resource = resource;
      clone.resourceGroup = resourceGroup;
      clone.priority = priority;
      // Isnt there a better way to copy over Collections?
      clone.timeAllocations = new ArrayList(timeAllocations.size());
      clone.timeAllocations.addAll(timeAllocations);
      clone.status = status;
      return clone;
   }
}
