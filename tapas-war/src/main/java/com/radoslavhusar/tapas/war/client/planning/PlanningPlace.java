package com.radoslavhusar.tapas.war.client.planning;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PlanningPlace extends Place {

   private Long projectId;

   public PlanningPlace(Long projectId) {
      this.projectId = projectId;
   }

   public Long getProjectId() {
      return projectId;
   }

   @Override
   public String toString() {
      return "PlanningPlace{projectId=" + projectId + "}";
   }

   public static class Tokenizer implements PlaceTokenizer<PlanningPlace> {

      @Override
      public String getToken(PlanningPlace place) {
         return "" + place.getProjectId();
      }

      @Override
      public PlanningPlace getPlace(String token) {
         try {
            return new PlanningPlace(Long.parseLong(token));
         } catch (NumberFormatException nfe) {
            return new PlanningPlace(null);
         }
      }
   }
}
