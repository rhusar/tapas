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

   private static final long serialVersionUID = 7440297955003302414L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column
   private String name;
   @Column
   private String summary;
   @ManyToOne(fetch = FetchType.LAZY)
   private Resource resource;
   @Column
   private Byte priority;
   @ManyToOne(fetch = FetchType.LAZY)
   private Project project;
   @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
   private List<TaskTimeAllocation> timeAllocations;
   @Column
   private TaskStatus status;
//   @ManyToOne(fetch = FetchType.EAGER)
//   @JoinColumn
//   private ResourceGroup resourceGroup;
//   public Resource getAssignee() {
//      return assignee;
//   }
//
//   public void setAssignee(Resource assignee) {
//      this.assignee = assignee;
//   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
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
   public List<TaskTimeAllocation> getTimeAllocations() {
      return timeAllocations;
   }

   public void setTimeAllocations(List<TaskTimeAllocation> taskTimeAllocations) {
      this.timeAllocations = taskTimeAllocations;
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

   @Override
   public String toString() {
      return "Task{id=" + id;
//      return "Task{id=" + id + ",name=" + name + ",summary=" + summary + ",assignee=" + assignee + '}';
   }

   public static String formatState(TaskStatus state) {
      return state.toString().substring(0, 1).toUpperCase() + state.toString().substring(1).toLowerCase();
   }
}
