package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.stats.ResourcePriorityAllocationStats;
import java.util.List;

public interface ResourceFacadeLocal {

   // CRUD
   void create(Resource resource);

   void edit(Resource resource);

   void remove(Resource resource);

   Resource find(Object id);

   List<Resource> findAll();

   List<Resource> findRange(int[] range);

   int count();

   // CUSTOM
   /**
    * Initializes allocation but only for the selected project!
    * @param projectId
    * @return  
    */
   List<Resource> findAllForProject(long projectId);

   ResourcePriorityAllocationStats tallyResourceDataForProject(long resourceId, long projectId);

   List<Resource> findAllNotOnProject(long projectId);
}
