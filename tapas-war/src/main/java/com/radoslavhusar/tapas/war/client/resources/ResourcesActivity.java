package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.radoslavhusar.tapas.war.client.app.Application;

public class ResourcesActivity extends AbstractActivity implements ResourcesView.Presenter {

   ResourcesViewImpl view;

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      view = Application.getInjector().getResourcesView();
      view.setPresenter(this);
      view.bind();
      panel.setWidget(view.asWidget());
   }

   @Override
   public void onStop() {
      view.unbind();
   }
}
