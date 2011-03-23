package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
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
@Local(ProjectFacadeLocal.class)
public class ProjectFacade extends AbstractFacade<Project> implements ProjectFacadeLocal {

   /**
    * Or could be   
    * @PersistenceContext(unitName = "TapasPersistenceUnit")
    * but see article: TBD
    */ 
   @PersistenceContext
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ProjectFacade() {
      super(Project.class);
   }
}
