package com.radoslavhusar.tapas.war.client.ui;

import com.google.gwt.user.client.Window;

public class MenuPresenter implements MenuView.Presenter {

   @Override
   public void doAbout() {
      Window.alert("Dummy log out.");
   }
}
