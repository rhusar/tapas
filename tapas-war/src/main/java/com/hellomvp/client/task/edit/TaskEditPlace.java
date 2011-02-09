package com.hellomvp.client.task.edit;

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
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
public class TaskEditPlace extends Place {

   private String helloName;

   public TaskEditPlace(String token) {
      this.helloName = token;
   }

   public String getHelloName() {
      return helloName;
   }

   public static class Tokenizer implements PlaceTokenizer<TaskEditPlace> {

      @Override
      public String getToken(TaskEditPlace place) {
         return place.getHelloName();
      }

      @Override
      public TaskEditPlace getPlace(String token) {
         return new TaskEditPlace(token);
      }
   }
}
