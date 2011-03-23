package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
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
@Local(ProjectPhaseFacadeLocal.class)
public class ProjectPhaseFacade extends AbstractFacade<ProjectPhase> implements ProjectPhaseFacadeLocal {

   @PersistenceContext
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ProjectPhaseFacade() {
      super(ProjectPhase.class);
   }
}
