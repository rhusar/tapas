package com.radoslavhusar.tapas.war.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.entity.ResourceProjectAllocation;
import com.radoslavhusar.tapas.ejb.entity.Task;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RemoteServiceRelativePath("res")
public interface TaskResourceService extends RemoteService {

   @Deprecated
   int getCount() throws Exception;

   @Deprecated
   void create(Task task);

   @Deprecated
   void edit(Task task);

   @Deprecated
   void remove(Task task);

   @Deprecated
   Task find(long id);

   @Deprecated
   List<Task> findAll();

   @Deprecated
   List<Task> findRange(int[] range);

   // Project
   List<Project> findAllProjects();

   void editProject(Project project);

   // Task
   void editTasks(Collection<Task> tasks);

   List<Task> findAllTasksForProject(long projectId);

   @Deprecated
   void editTasksForProject(long projectId, Collection<Task> tasks);

   // Group
   List<ResourceGroup> findAllGroups();

   // Resource
   List<Resource> findAllResourcesForProject(long projectId);

   List<ResourceProjectAllocation> findAllAllocationsForProject(long projectId);

   Map<Resource, Double[]> findAllResourceStatsForProject(long projectId);

   void editResourcesForProject(long projectId, Collection<Resource> resources);
}
