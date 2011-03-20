package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
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
@Local(TimeAllocationFacadeLocal.class)
public class TimeAllocationFacade extends AbstractFacade<TimeAllocation> implements TimeAllocationFacadeLocal {

   @PersistenceContext(unitName = "TapasPersistenceUnit")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public TimeAllocationFacade() {
      super(TimeAllocation.class);
   }
}
