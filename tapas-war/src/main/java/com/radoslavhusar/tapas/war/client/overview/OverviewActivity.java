package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;

public class OverviewActivity extends AbstractActivity implements OverviewView.Presenter {

   OverviewViewImpl view;
   private final TaskResourceServiceAsync service;
   private final ClientState cache;

   @Inject
   public OverviewActivity(OverviewViewImpl view, ClientState cache, TaskResourceServiceAsync service) {
      this.view = view;
      this.cache = cache;
      this.service = service;
   }

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      //view = Application.getInjector().getOverviewView();
      view.setPresenter(this);
      view.bind();
      panel.setWidget(view.asWidget());

      //GWT.log(Application.getInjector().getPlaceController().getWhere().getClass().getName());
      //PlaceControllerGin pcg = Application.getInjector().getPlaceController();
      //GWT.log("" + ((pcg.getFutureWhere().equals(Place.NOWHERE))));// instanceof OverviewPlace));
   }

   @Override
   public void onStop() {
      //super.onStop();
      view.unbind();
   }
}
