package com.radoslavhusar.tapas.war.client.planning;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PlanningPlace extends Place {

   public static class Tokenizer implements PlaceTokenizer<PlanningPlace> {

      @Override
      public String getToken(PlanningPlace place) {
         return null;
      }

      @Override
      public PlanningPlace getPlace(String token) {

         return new PlanningPlace();
      }
   }
}
