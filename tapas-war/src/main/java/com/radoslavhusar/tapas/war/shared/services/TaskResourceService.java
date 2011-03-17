package com.radoslavhusar.tapas.war.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.entity.ResourceProjectAllocation;
import com.radoslavhusar.tapas.ejb.entity.Task;
import java.util.List;
import java.util.Map;

@RemoteServiceRelativePath("res")
public interface TaskResourceService extends RemoteService {

   // Project
   List<Project> findAllProjects();

//   List<Resource> findAllResources();

   int getCount() throws Exception;

   void create(Task task);

   void edit(Task task);

   void remove(Task task);

   Task find(long id);

   List<Task> findAll();

   List<Task> findRange(int[] range);

   void editProject(Project project);

   public List<ResourceProjectAllocation> findAllAllocationsForProject(Project project);

   // Resource
//   public List<Resource> findAllResourcesForProject(long projectId);

   public Map<Resource, Double[]> findAllResourceStatsForProject(long projectId);

//   public List<ResourceWrap> findAllResourceWrapsForProject(long projectId);
   // Group
   public List<ResourceGroup> findAllGroups();
}
