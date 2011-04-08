package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.stats.ResourceAllocationStatsEntry;
import com.radoslavhusar.tapas.ejb.stats.ResourcePhaseStatsEntry;
import java.util.List;

public interface ResourceFacadeLocal {

   // CRUD
   void create(Resource resource);

   void edit(Resource resource);

   void editOrCreate(Resource entity);

   void remove(Resource resource);

   Resource find(Object id);

   List<Resource> findAll();

   List<Resource> findRange(int[] range);

   int count();

   // CUSTOM
   /**
    * NOTE: Initializes lazy allocation but only for the selected project!
    * 
    * @param projectId
    * @return  
    */
   List<Resource> findAllForProject(long projectId);

   List<Resource> findAllNotOnProject(long projectId);

   ResourceAllocationStatsEntry tallyResourceStatsForProject(long resourceId, long projectId);

   /**
    * Contains resource, allocated, completed
    * 
    * @param phaseId 
    * @return ResourcePhaseStatsEntry list
    */
   List<ResourcePhaseStatsEntry> tallyResourcesStatsForPhase(long phaseId);
}
