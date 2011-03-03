package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class OverviewPlace extends Place {

   private long projectId;

   public OverviewPlace(long projectId) {
      this.projectId = projectId;
   }

   public long getProjectId() {
      return projectId;
   }

   public static class Tokenizer implements PlaceTokenizer<OverviewPlace> {

      @Override
      public String getToken(OverviewPlace place) {
         return "" + place.getProjectId();
      }

      @Override
      public OverviewPlace getPlace(String token) {
         long projectId = Long.valueOf(token);
         return new OverviewPlace(projectId);
      }
   }
}
