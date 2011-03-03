package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class OverviewPlace extends Place {

   private String selectedProjectId;

   public OverviewPlace(String token) {
      this.selectedProjectId = token;
   }

   public OverviewPlace() {
      selectedProjectId = null;
   }

   public String getSelectedTaskId() {
      return selectedProjectId;
   }

   public static class Tokenizer implements PlaceTokenizer<OverviewPlace> {

      @Override
      public String getToken(OverviewPlace place) {
         return place.getSelectedTaskId();
      }

      @Override
      public OverviewPlace getPlace(String token) {
         return new OverviewPlace(token);
      }
   }
}
