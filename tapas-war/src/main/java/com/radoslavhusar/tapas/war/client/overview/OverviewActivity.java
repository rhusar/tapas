package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.radoslavhusar.tapas.war.client.app.Application;

public class OverviewActivity extends AbstractActivity implements OverviewView.Presenter {

   OverviewViewImpl view;

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      view = Application.getInjector().getOverviewView();
      view.setPresenter(this);
      view.bind();
      panel.setWidget(view.asWidget());
   }

   @Override
   public void onStop() {
      super.onStop();
      view.unbind();
   }
}
