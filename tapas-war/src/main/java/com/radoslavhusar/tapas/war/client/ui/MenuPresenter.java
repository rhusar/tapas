package com.radoslavhusar.tapas.war.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.overview.OverviewPlace;

public class MenuPresenter implements MenuView.Presenter {

   @Override
   public void doAbout() {
      Window.alert("Dummy log out.");
   }

   @Override
   public void switchProject(Project selected) {
      GWT.log("Switched to project: " + selected.getName());
      Application.getInjector().getClientState().setProject(selected);
      Application.getInjector().getPlaceController().goTo(new OverviewPlace());
   }
}
