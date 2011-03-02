package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.PlaceControllerGin;

public class OverviewActivity extends AbstractActivity implements OverviewView.Presenter {

   OverviewViewImpl view;

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      view = Application.getInjector().getOverviewView();
      view.setPresenter(this);
      //GWT.log(Application.getInjector().getPlaceController().getWhere().getClass().getName());
      PlaceControllerGin pcg = Application.getInjector().getPlaceController();
//      GWT.log("" + ((pcg.getFutureWhere().equals(Place.NOWHERE))));// instanceof OverviewPlace));
      view.bind();
      panel.setWidget(view.asWidget());
   }

   @Override
   public void onStop() {
      super.onStop();
      view.unbind();
   }
}
