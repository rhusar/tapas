package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.stats.ResourceAllocationStatsEntry;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.Trait;
import com.radoslavhusar.tapas.ejb.stats.ProjectStats;
import com.radoslavhusar.tapas.ejb.stats.ResourcePhaseStatsEntry;
import com.radoslavhusar.tapas.war.client.event.DataReadyEvent;
import com.radoslavhusar.tapas.war.client.event.DataType;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.List;
import java.util.Map;

/**
 * Singleton which controls state and cache of the client application.
 * 
 * TODO: consider maybe rename to ClientCache?
 */
@Singleton
public class ClientState {

   private final EventBus eventBus;
   private final TaskResourceServiceAsync service;
   private Long projectId;
   private Project project;
   private List<Resource> resources;
   private Map<Long, ResourceAllocationStatsEntry> resourceData;
   private List<ResourceGroup> groups;
   private List<Task> tasks;
   private List<Trait> traits;
   private Map<Long, List<ResourcePhaseStatsEntry>> resourcePhaseStats;
   private ProjectStats projectStats;

   //private volatile int toSync = 3;
   @Inject
   public ClientState(EventBus bus, TaskResourceServiceAsync service) {
      this.eventBus = bus;
      this.service = service;
   }

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
      //resourceAllocations = null;
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
            eventBus.fireEvent(new DataReadyEvent(DataType.PROJECT));
         }
      });
      service.findAllGroups(new AsyncCallback<List<ResourceGroup>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<ResourceGroup> result) {
            groups = result;
            eventBus.fireEvent(new DataReadyEvent(DataType.GROUPS));
         }
      });
      service.findAllTraits(new AsyncCallback<List<Trait>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Trait> result) {
            traits = result;
            eventBus.fireEvent(new DataReadyEvent(DataType.TRAITS));
         }
      });
   }

   public Project getProject() {
      return project;
   }

   public List<ResourceGroup> getGroups() {
      return groups;
   }

   public void setGroups(List<ResourceGroup> groups) {
      this.groups = groups;
   }

   public Map<Long, ResourceAllocationStatsEntry> getResourceData() {
      return resourceData;
   }

   public void setResourceData(Map<Long, ResourceAllocationStatsEntry> resourceData) {
      this.resourceData = resourceData;
   }

   public List<Resource> getResources() {
      return resources;
   }

   public void setResources(List<Resource> resources) {
      this.resources = resources;
   }

   public void setTasks(List<Task> tasks) {
      this.tasks = tasks;

   }

   public List<Task> getTasks() {
      return tasks;
   }

   public List<Trait> getTraits() {
      return traits;
   }

   public void setTraits(List<Trait> traits) {
      this.traits = traits;
   }

   // Resource stats routines
   public Map<Long, List<ResourcePhaseStatsEntry>> getResourceStats() {
      return resourcePhaseStats;
   }

   public void setResourceStats(Map<Long, List<ResourcePhaseStatsEntry>> resourcePhaseStats) {
      this.resourcePhaseStats = resourcePhaseStats;
   }

   public void prepareResourceStats(long phaseId) {
      service.tallyResourcePhaseStatsForProject(phaseId, new AsyncCallback<Map<Long, List<ResourcePhaseStatsEntry>>>() {

         @Override
         public void onFailure(Throwable caught) {
            // ...some exception handling needed
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(Map<Long, List<ResourcePhaseStatsEntry>> result) {
            GWT.log("Resource Phase Stats ready: " + result);
            resourcePhaseStats = result;
            eventBus.fireEvent(new DataReadyEvent(DataType.RESOURCE_STATS));
         }
      });
   }
   // Project Stats

   public ProjectStats getProjectStats() {
      return projectStats;
   }

   public void setProjectStats(ProjectStats projectStats) {
      this.projectStats = projectStats;
   }

   public void prepareProjectStats() {
      service.tallyProjectStats(projectId, new AsyncCallback<ProjectStats>() {

         @Override
         public void onFailure(Throwable caught) {
            // ...some exception handling needed
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(ProjectStats result) {
            GWT.log("Project Stats ready: " + result);
            projectStats = result;
            eventBus.fireEvent(new DataReadyEvent(DataType.PROJECT_STATS));
         }
      });
   }
}
