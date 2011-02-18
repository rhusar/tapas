package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TASK")
public class Task implements Serializable {

   private static final long serialVersionUID = 7440297955003302414L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @Column
   private String name;
   @Column
   private String summary;
   @Column
   @ManyToOne
   private Resource assignee;
   @Column
   private Byte priority;
   @Column
   private TaskState status;
   @ManyToOne
   private ResourceGroup resourceGroup;

   public Resource getAssignee() {
      return assignee;
   }

   public void setAssignee(Resource assignee) {
      this.assignee = assignee;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
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

   public ResourceGroup getResourceGroup() {
      return resourceGroup;
   }

   public void setResourceGroup(ResourceGroup resourceGroup) {
      this.resourceGroup = resourceGroup;
   }

   public TaskState getStatus() {
      return status;
   }

   public void setStatus(TaskState status) {
      this.status = status;
   }

   public String getSummary() {
      return summary;
   }

   public void setSummary(String summary) {
      this.summary = summary;
   }
}
