/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package com.radoslavhusar.tapas.ejb.session;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
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
