package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
@Stateless
public class ProjectFacade extends AbstractFacade<Project> implements ProjectFacadeLocal {

   @PersistenceContext(unitName = "MyPersistenceUnit")
   private EntityManager em;

   protected EntityManager getEntityManager() {
      return em;
   }

   public ProjectFacade() {
      super(Project.class);
   }
}
