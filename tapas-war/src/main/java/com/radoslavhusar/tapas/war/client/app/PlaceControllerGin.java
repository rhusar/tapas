package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

public class PlaceControllerGin extends PlaceController {

   @Inject
   public PlaceControllerGin(EventBus eventBus) {
      super(eventBus);
   }

//   @Override
//   public void goTo(Place newPlace) {
//      System.out.println("going to " + newPlace);
//      super.goTo(newPlace);
//   }
}
