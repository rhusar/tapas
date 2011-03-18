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

   int getCount() throws Exception;

   void create(Task task);

   void edit(Task task);

   void remove(Task task);

   Task find(long id);

   List<Task> findAll();

   List<Task> findRange(int[] range);

   // Project
   List<Project> findAllProjects();

   void editProject(Project project);

   // Group
   public List<ResourceGroup> findAllGroups();

   // Resource
   public List<Resource> findAllResourcesForProject(long projectId);

   public List<ResourceProjectAllocation> findAllAllocationsForProject(long projectId);

   public Map<Resource, Double[]> findAllResourceStatsForProject(long projectId);

   public void editResourcesForProject(long projectId, Collection<Resource> resources);
}
