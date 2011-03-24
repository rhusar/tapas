package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class OverviewPlace extends Place {

   private Long projectId;

   public OverviewPlace(Long projectId) {
      this.projectId = projectId;
   }

   public Long getProjectId() {
      return projectId;
   }

   @Override
   public String toString() {
      return "OverviewPlace{projectId=" + projectId + "}";
   }

   public static class Tokenizer implements PlaceTokenizer<OverviewPlace> {

      @Override
      public String getToken(OverviewPlace place) {
         return "" + place.getProjectId();
      }

      @Override
      public OverviewPlace getPlace(String token) {
         try {
            return new OverviewPlace(Long.parseLong(token));
         } catch (NumberFormatException nfe) {
            return new OverviewPlace(null);
         }
      }
   }
}
