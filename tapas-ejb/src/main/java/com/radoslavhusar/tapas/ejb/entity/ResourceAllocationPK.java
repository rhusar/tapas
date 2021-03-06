package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ResourceAllocationPK implements Serializable {

   private static final long serialVersionUID = 1L;
   @ManyToOne
   private Resource resource;
   @ManyToOne
   private Project project;

   public ResourceAllocationPK() {
   }

   public ResourceAllocationPK(Project project, Resource resource) {
      this.project = project;
      this.resource = resource;
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
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final ResourceAllocationPK other = (ResourceAllocationPK) obj;
      if (this.resource != other.getResource() && (this.resource == null || !this.resource.equals(other.getResource()))) {
         return false;
      }
      if (this.project != other.getProject() && (this.project == null || !this.project.equals(other.getProject()))) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 3;
      hash = 97 * hash + (this.resource != null ? this.resource.hashCode() : 0);
      hash = 97 * hash + (this.project != null ? this.project.hashCode() : 0);
      return hash;
   }
}
