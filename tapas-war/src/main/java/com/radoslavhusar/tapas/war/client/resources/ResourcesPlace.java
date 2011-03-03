package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ResourcesPlace extends Place {

   public static class Tokenizer implements PlaceTokenizer<ResourcesPlace> {

      @Override
      public String getToken(ResourcesPlace place) {
         return null;
      }

      @Override
      public ResourcesPlace getPlace(String token) {

         return new ResourcesPlace();
      }
   }
}
