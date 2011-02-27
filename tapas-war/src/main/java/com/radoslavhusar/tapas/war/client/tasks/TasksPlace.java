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
package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
public class TasksPlace extends Place {

   private String selectedTaskId;

   public TasksPlace(String token) {
      this.selectedTaskId = token;
   }

   public TasksPlace() {
      selectedTaskId = null;
   }

   public String getSelectedTaskId() {
      return selectedTaskId;
   }

   public static class Tokenizer implements PlaceTokenizer<TasksPlace> {

      @Override
      public String getToken(TasksPlace place) {
         return place.getSelectedTaskId();
      }

      @Override
      public TasksPlace getPlace(String token) {
         return new TasksPlace(token);
      }
   }
}
