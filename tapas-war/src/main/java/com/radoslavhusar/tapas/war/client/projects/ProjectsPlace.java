package com.radoslavhusar.tapas.war.client.projects;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProjectsPlace extends Place {

   @Override
   public String toString() {
      return "ProjectsPlace{}";
   }

   public static class Tokenizer implements PlaceTokenizer<ProjectsPlace> {

      @Override
      public String getToken(ProjectsPlace place) {
         return "";
      }

      @Override
      public ProjectsPlace getPlace(String token) {
         return new ProjectsPlace();
      }
   }
}
