package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ResourceAllocationFacadeLocal.class)
public class ResourceAllocationFacade extends AbstractFacade<ResourceAllocation> implements ResourceAllocationFacadeLocal {

   @PersistenceContext
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ResourceAllocationFacade() {
      super(ResourceAllocation.class);
   }

   @Override
   public List<ResourceAllocation> findAllForProject(Project project) {
      if (project == null) {
         return new ArrayList<ResourceAllocation>();
      }

      return findAllForProject(project.getId());
   }

   @Override
   public List<ResourceAllocation> findAllForProject(long projectId) {
      return getEntityManager().createNamedQuery("resourceAllocationsForProject").setParameter("projectid", projectId).getResultList();
   }
}
