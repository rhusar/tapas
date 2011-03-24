package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ResourcesPlace extends Place {

   private Long projectId;

   public ResourcesPlace(Long projectId) {
      this.projectId = projectId;
   }

   public Long getProjectId() {
      return projectId;
   }

   public static class Tokenizer implements PlaceTokenizer<ResourcesPlace> {

      @Override
      public String getToken(ResourcesPlace place) {
         return "" + place.getProjectId();
      }

      @Override
      public ResourcesPlace getPlace(String token) {
         try {
            return new ResourcesPlace(Long.parseLong(token));
         } catch (NumberFormatException nfe) {
            return new ResourcesPlace(null);
         }
      }
   }
}
