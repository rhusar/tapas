package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

public class PlaceControllerGin extends PlaceController {

   private Place futurePlace;

   @Inject
   public PlaceControllerGin(EventBus eventBus) {
      super(eventBus);
   }

   @Override
   public void goTo(Place newPlace) {
      //GWT.log("going to " + newPlace);
      futurePlace = newPlace;
      super.goTo(newPlace);
   }

   public Place getFutureWhere() {
      return futurePlace;
   }
}
