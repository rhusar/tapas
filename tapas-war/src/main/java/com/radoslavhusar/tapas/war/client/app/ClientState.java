package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocation;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocationData;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.List;
import java.util.Map;

/**
 * Singleton which controls state and cache of the application.
 * 
 * TODO : maybe rename to ClientCache?
 */
@Singleton
public class ClientState {

   private TaskResourceServiceAsync service;
   private Long projectId;
   private Project project;
   private List<Resource> resources;
   private Map<Long, ResourceAllocation> resourceAllocations;
   private Map<Long, ResourceAllocationData> resourceData;
   private List<ResourceGroup> groups;

   //private volatile int toSync = 3;
   @Inject
   public ClientState(TaskResourceServiceAsync service) {
      this.service = service;
      /*
      res.findAllProjects(new AsyncCallback<List<Project>>() {
      
      @Override
      public void onFailure(Throwable caught) {
      GWT.log("ClientState error fetching Project.");
      toSync = 0;
      }
      
      @Override
      public void onSuccess(List<Project> result) {
      project = result.get(0);
      
      Application.getInjector().getService().findAllResourceStatsForProject(project.getId(), new AsyncCallback<Map<Resource, Double[]>>() {
      
      @Override
      public void onFailure(Throwable caught) {
      throw new UnsupportedOperationException("Not supported yet.");
      }
      
      @Override
      public void onSuccess(Map<Resource, Double[]> result) {
      setResources(result);
      doSync();
      }
      });
      
      doSync();
      }
      });
      
      
       */
   }
   /*
   public void doSync() {
   toSync--;
   GWT.log("Initial loading services to synchronize remaining: " + toSync);
   
   if (toSync == 0) {
   Application.getInjector().getPlaceController().goTo(new TasksPlace());
   }
   }*/

   public Long getProjectId() {
      return projectId;
   }

   public void setProjectId(Long projectId) {
      if (projectId == null || projectId.equals(this.projectId)) {
         return;
      }

      this.projectId = projectId;

      // Now is the place for big reset
      project = null;
      resourceAllocations = null;
      resources = null;

      // Start prefilling the cache
      service.findProject(projectId, new AsyncCallback<Project>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(Project result) {
            project = result;
         }
      });
      service.findAllResourcesForProject(projectId, new AsyncCallback<List<Resource>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Resource> result) {
            resources = result;
         }
      });
      /*service.findAllAllocationsForProject(projectId, new AsyncCallback<Map<Long, ResourceAllocation>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(Map<Long, ResourceAllocation> result) {
            resourceAllocations = result;
         }
      });*/
      service.findAllGroups(new AsyncCallback<List<ResourceGroup>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<ResourceGroup> result) {
            groups = result;

         }
      });
   }

   public Project getProject() {
      return project;
   }

   @SuppressWarnings("ReturnOfCollectionOrArrayField")
   public List<ResourceGroup> getGroups() {
      return groups;
   }

   public void setGroups(List<ResourceGroup> groups) {
      this.groups = groups;
   }

   public Map<Long, ResourceAllocation> getResourceAllocations() {
      return resourceAllocations;
   }

   public void setResourceAllocations(Map<Long, ResourceAllocation> resourceAllocations) {
      this.resourceAllocations = resourceAllocations;
   }

   public Map<Long, ResourceAllocationData> getResourceData() {
      return resourceData;
   }

   public void setResourceData(Map<Long, ResourceAllocationData> resourceData) {
      this.resourceData = resourceData;
   }

   public List<Resource> getResources() {
      return resources;
   }

   public void setResources(List<Resource> resources) {
      this.resources = resources;
   }
}
