package com.radoslavhusar.tapas.war.client.reports;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ReportsPlace extends Place {

   private String taskId;

   public ReportsPlace(String token) {
      this.taskId = token;
   }

   public String getTaskId() {
      return taskId;
   }

   public static class Tokenizer implements PlaceTokenizer<ReportsPlace> {

      @Override
      public String getToken(ReportsPlace place) {
         return place.getTaskId();
      }

      @Override
      public ReportsPlace getPlace(String token) {
         return new ReportsPlace(token);
      }
   }
}
