package com.radoslavhusar.tapas.war.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocationData;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.Trait;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RemoteServiceRelativePath("res")
public interface TaskResourceService extends RemoteService {

   // Project
   Project findProject(long projectId);

   List<Project> findAllProjects();

   void editProject(Project project);

   // Trait
   List<Trait> findAllTraits();

   // Task
   void editTasks(Collection<Task> tasks);

   List<Task> findAllTasksForProject(long projectId);

   void editTasksForProject(long projectId, Collection<Task> tasks);

   // Resource
   List<Resource> findAllResourcesForProject(long projectId);

   /*Map<Long, ResourceAllocation> findAllAllocationsForProject(long projectId);*/
   Map<Long, ResourceAllocationData> fetchAllResourceDataForProject(long projectId);

   void editResourcesForProject(long projectId, Collection<Resource> resources);

   // Group
   List<ResourceGroup> findAllGroups();
}
