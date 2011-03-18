package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ResourceProjectAllocation;
import java.util.List;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
public interface ResourceProjectAllocationFacadeLocal {

   // CRUD
   void create(ResourceProjectAllocation resourceProjectAllocation);

   void edit(ResourceProjectAllocation resourceProjectAllocation);

   void remove(ResourceProjectAllocation resourceProjectAllocation);

   ResourceProjectAllocation find(Object id);

   List<ResourceProjectAllocation> findAll();

   List<ResourceProjectAllocation> findRange(int[] range);

   int count();

   // NON-CRUD
   List<ResourceProjectAllocation> findAllForProject(Project project);

   List<ResourceProjectAllocation> findAllForProject(long projectId);
}
