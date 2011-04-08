package com.radoslavhusar.tapas.war.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.stats.ResourceAllocationStatsEntry;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.Trait;
import com.radoslavhusar.tapas.ejb.stats.ProjectStats;
import com.radoslavhusar.tapas.ejb.stats.ResourcePhaseStatsEntry;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RemoteServiceRelativePath("res")
public interface TaskResourceService extends RemoteService {

   // Project
   Project findProject(long projectId);

   List<Project> findAllProjects();

   void editProject(Project project);

   Long createProject(Project project);

   ProjectStats tallyProjectStats(long projectId);

   Map<Long, List<ResourcePhaseStatsEntry>> tallyResourcePhaseStatsForProject(long projectId);

   // Trait
   List<Trait> findAllTraits();

   void createTrait(Trait trait);

   // Task
   List<Task> findAllTasksForProject(long projectId);

   /**
    * @param task to update or a new task
    * @return updated task - so in a case of new tasks the generated ID is returned in task
    */
   Task editTask(Task task);

   /*@Deprecated
   void editTasks(Collection<Task> tasks);*/

   /*void editTasksForProject(long projectId, Collection<Task> tasks);*/
   // Resource
   List<Resource> findAllResourcesForProject(long projectId);

   List<Resource> findAllResourcesNotOnProject(long projectId);

   void editResourcesForProject(long projectId, Collection<Resource> resources);

   Map<Long, ResourceAllocationStatsEntry> tallyResourceStatsForProject(long projectId);

   // Group
   List<ResourceGroup> findAllGroups();
}
