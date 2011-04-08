package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocation;
import java.util.List;

public interface ResourceAllocationFacadeLocal {

   // CRUD
   void create(ResourceAllocation resourceProjectAllocation);

   void edit(ResourceAllocation resourceProjectAllocation);

   void editOrCreate(ResourceAllocation entity);

   void remove(ResourceAllocation resourceProjectAllocation);

   ResourceAllocation find(Object id);

   List<ResourceAllocation> findAll();

   List<ResourceAllocation> findRange(int[] range);

   int count();

   // NON-CRUD
   List<ResourceAllocation> findAllForProject(Project project);

   List<ResourceAllocation> findAllForProject(long projectId);
}
