package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Singleton;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ResourceProjectAllocation;
import java.util.List;

/**
 * Singleton which controls state and cache of the application.
 */
@Singleton
public class ClientState {

   public ClientState() {
      Application.getInjector().getMyResourceService().findAllProjects(new AsyncCallback<List<Project>>() {

         @Override
         public void onFailure(Throwable caught) {
            GWT.log("ClientState error fetching projects.");
         }

         @Override
         public void onSuccess(List<Project> result) {
            project = result.get(0);
         }
      });
   }
   private Project project;
   private List<ResourceProjectAllocation> resourceAllocations;

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;

      resourceAllocations = null;
   }

   @SuppressWarnings("ReturnOfCollectionOrArrayField")
   public List<ResourceProjectAllocation> getResourceAllocations() {
      return resourceAllocations;
   }

   public void setResourceAllocations(List<ResourceProjectAllocation> resourceAllocations) {
      this.resourceAllocations = resourceAllocations;
   }
}
