package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocationData;
import java.util.List;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
//@Local
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
   List<Resource> findAllForProject(Project project);

   List<Resource> findAllForProject(long projectId);

   ResourceAllocationData fetchDataForProject(long resourceId, long projectId);
}
