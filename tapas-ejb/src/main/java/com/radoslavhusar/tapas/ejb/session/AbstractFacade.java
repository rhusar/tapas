package com.radoslavhusar.tapas.ejb.session;

import java.util.List;
import javax.persistence.EntityManager;

public abstract class AbstractFacade<T> {

   private Class<T> entityClass;

   public AbstractFacade(Class<T> entityClass) {
      this.entityClass = entityClass;
   }

   protected abstract EntityManager getEntityManager();

   public void create(T entity) {
      getEntityManager().persist(entity);
   }

   public void edit(T entity) {
      getEntityManager().merge(entity);
   }

   public void remove(T entity) {
      getEntityManager().remove(getEntityManager().merge(entity));
   }

   public T find(Object id) {
      return getEntityManager().find(entityClass, id);
   }

   public List<T> findAll() {
      return getEntityManager().createQuery("select object(o) from " + entityClass.getSimpleName() + " as o").getResultList();
   }

   public List<T> findRange(int[] range) {
      javax.persistence.Query q = getEntityManager().createQuery("select object(o) from " + entityClass.getSimpleName() + " as o");
      q.setMaxResults(range[1] - range[0]);
      q.setFirstResult(range[0]);
      return q.getResultList();
   }

   public int count() {
      return ((Long) getEntityManager().createQuery("select count(o) from " + entityClass.getSimpleName() + " as o").getSingleResult()).intValue();
   }
}
