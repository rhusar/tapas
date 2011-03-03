package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
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
@Local(ResourceGroupFacadeLocal.class)
public class ResourceGroupFacade extends AbstractFacade<ResourceGroup> implements ResourceGroupFacadeLocal {

   @PersistenceContext(unitName = "MyPersistenceUnit")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ResourceGroupFacade() {
      super(ResourceGroup.class);
   }
}
