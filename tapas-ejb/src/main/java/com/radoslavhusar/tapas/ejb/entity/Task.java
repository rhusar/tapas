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
@Table(name = "TASK")
public class Task implements Serializable {

   private static final long serialVersionUID = 7440293452003302414L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
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

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
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

   @Override
   public String toString() {
      return "Task{id=" + id + "|" + unifiedId
              + ",name=" + name
              + ",assignee=" + resource == null ? null : resource.getName() + "}";
   }

   public static String formatState(TaskStatus state) {
      return state == null ? "" : state.toString().substring(0, 1).toUpperCase() + state.toString().substring(1).toLowerCase();
   }
}
