package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.ResourceProjectAllocation;
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
@Local(ResourceProjectAllocationFacadeLocal.class)
public class ResourceProjectAllocationFacade extends AbstractFacade<ResourceProjectAllocation> implements ResourceProjectAllocationFacadeLocal {

   @PersistenceContext(unitName = "MyPersistenceUnit")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ResourceProjectAllocationFacade() {
      super(ResourceProjectAllocation.class);
   }
}
