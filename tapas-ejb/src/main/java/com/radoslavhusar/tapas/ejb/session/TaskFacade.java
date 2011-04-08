package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Task;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(TaskFacadeLocal.class)
public class TaskFacade extends AbstractFacade<Task> implements TaskFacadeLocal {

   @PersistenceContext
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public TaskFacade() {
      super(Task.class);
   }

   @Override
   public void editOrCreate(Task entity) {
      if (entity.getId() == null) {
         create(entity);
      } else {
         edit(entity);
      }
   }

   @Override
   public List<Task> findAllForProject(long projectId) {
      return getEntityManager().
              createNamedQuery("tasksForProject").
              setParameter("projectid", projectId).
              getResultList();
   }
}
