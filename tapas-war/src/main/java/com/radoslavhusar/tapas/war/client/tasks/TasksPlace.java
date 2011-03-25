package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TasksPlace extends Place {

   private Long projectId;

   public TasksPlace(Long projectId) {
      this.projectId = projectId;
   }

   public Long getProjectId() {
      return projectId;
   }

   public static class Tokenizer implements PlaceTokenizer<TasksPlace> {

      @Override
      public String getToken(TasksPlace place) {
         return "" + place.getProjectId();
      }

      @Override
      public TasksPlace getPlace(String token) {
         try {
            return new TasksPlace(Long.parseLong(token));
         } catch (NumberFormatException nfe) {
            return new TasksPlace(null);
         }
      }
   }
}
