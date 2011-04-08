package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Trait;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(TraitFacadeLocal.class)
public class TraitFacade extends AbstractFacade<Trait> implements TraitFacadeLocal {

   @PersistenceContext
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public TraitFacade() {
      super(Trait.class);
   }

   @Override
   public void editOrCreate(Trait entity) {
      if (entity.getId() == null) {
         create(entity);
      } else {
         edit(entity);
      }
   }

   /**
    * Override to fetch them sorted.
    * 
    * @return sorted list
    */
   @Override
   public List<Trait> findAll() {
      return getEntityManager().createNamedQuery("findAllTraitsSorted").getResultList();
   }
}
