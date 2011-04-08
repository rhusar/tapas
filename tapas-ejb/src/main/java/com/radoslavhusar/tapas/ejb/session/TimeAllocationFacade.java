package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(TimeAllocationFacadeLocal.class)
public class TimeAllocationFacade extends AbstractFacade<TimeAllocation> implements TimeAllocationFacadeLocal {

   @PersistenceContext
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public TimeAllocationFacade() {
      super(TimeAllocation.class);
   }

   @Override
   public void editOrCreate(TimeAllocation entity) {
      if (entity.getId() == null) {
         create(entity);
      } else {
         edit(entity);
      }
   }
}
