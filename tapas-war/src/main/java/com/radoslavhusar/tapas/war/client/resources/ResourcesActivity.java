package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.radoslavhusar.tapas.war.client.app.Application;

public class ResourcesActivity extends AbstractActivity implements ResourcesView.Presenter {

   ResourcesViewImpl view;
   private boolean isUnsaved = false;

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      view = Application.getInjector().getResourcesView();
      view.setPresenter(this);
      view.bind();
      panel.setWidget(view.asWidget());
   }

   @Override
   public String mayStop() {
      if (isUnsaved) {
         //return "Unsaved changes will be lost - do you want to continue?";
      }

      return null;
   }

   @Override
   public void onStop() {
      view.unbind();
   }

   @Override
   public void setUnsaved(boolean b) {
      isUnsaved = true;
   }
}
