package com.radoslavhusar.tapas.war.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.client.app.PlaceControllerGin;
import com.radoslavhusar.tapas.war.client.overview.OverviewPlace;

public class MenuPresenter implements MenuView.Presenter {

   private ClientState client;
   private PlaceController placeControl;

   @Inject
   public MenuPresenter(ClientState client, PlaceControllerGin placeControl) {
      GWT.log("MenuPresenter created.");

      this.client = client;
      this.placeControl = placeControl;
   }

   @Override
   public void doAbout() {
      Window.alert("Not implemented yet.");
   }

   @Override
   public void switchProject(Project selected) {
      GWT.log("Switched to project: " + selected.getName() + " (ID: " + selected.getId() + ")");

      client.setProjectId(selected.getId());
      placeControl.goTo(new OverviewPlace(selected.getId()));
   }
}
