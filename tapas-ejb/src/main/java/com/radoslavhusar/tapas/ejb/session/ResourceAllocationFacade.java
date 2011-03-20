package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ResourceProjectAllocation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
@Stateless
@Local(ResourceAllocationFacadeLocal.class)
public class ResourceAllocationFacade extends AbstractFacade<ResourceProjectAllocation> implements ResourceAllocationFacadeLocal {

   @PersistenceContext(unitName = "TapasPersistenceUnit")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ResourceAllocationFacade() {
      super(ResourceProjectAllocation.class);
   }

   @Override
   public List<ResourceProjectAllocation> findAllForProject(Project project) {
      if (project == null) {
         return new ArrayList<ResourceProjectAllocation>();
      }

      return findAllForProject(project.getId());
   }

   @Override
   public List<ResourceProjectAllocation> findAllForProject(long projectId) {
      return getEntityManager().
              createQuery("select object(o) from " + ResourceProjectAllocation.class.getSimpleName() + " as o where o.key.project.id = :projectid").
              setParameter("projectid", projectId).
              getResultList();
   }
}
