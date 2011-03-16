package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ResourceFacadeLocal.class)
public class ResourceFacade extends AbstractFacade<Resource> implements ResourceFacadeLocal {

   @PersistenceContext(unitName = "TapasPersistenceUnit")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ResourceFacade() {
      super(Resource.class);
   }

   @Override
   public List<Resource> findAllForProject(Project project) {
      return this.findAllForProject(project.getId());
   }

   @Override
   public List<Resource> findAllForProject(long projectId) {
      // Do an inner join - fetch only assigned to the project.
      return getEntityManager().
              createQuery("select object(o) from " + Resource.class.getSimpleName() + " as o "
              + "inner join o.resourceProjectAllocations as a "
              + " where a.project.id = :projectid").
              setParameter("projectid", projectId).
              getResultList();
   }

   @Override
   public Double[] statForProject(Resource resource, long projectId) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
