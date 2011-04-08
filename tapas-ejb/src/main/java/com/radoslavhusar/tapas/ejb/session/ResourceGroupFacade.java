package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ResourceGroupFacadeLocal.class)
public class ResourceGroupFacade extends AbstractFacade<ResourceGroup> implements ResourceGroupFacadeLocal {

   @PersistenceContext
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ResourceGroupFacade() {
      super(ResourceGroup.class);
   }

   @Override
   public void editOrCreate(ResourceGroup entity) {
      if (entity.getId() == null) {
         create(entity);
      } else {
         edit(entity);
      }
   }
}
