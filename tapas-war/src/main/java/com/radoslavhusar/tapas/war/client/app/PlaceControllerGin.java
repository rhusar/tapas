package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

public class PlaceControllerGin extends PlaceController {

   @Inject
   public PlaceControllerGin(EventBus eventBus) {
      super(eventBus);
   }

   /*
   private Place futurePlace;
   @Override
   public void goTo(Place newPlace) {
   //GWT.log("going to " + newPlace);
   futurePlace = newPlace;
   super.goTo(newPlace);
   }
   
   public Place getFutureWhere() {
   return futurePlace;
   }
    */
}
