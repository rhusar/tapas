package com.radoslavhusar.tapas.war.client.planning;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class PlanningActivity extends AbstractActivity implements PlanningView.Presenter {

   private final PlanningView view;

   @Inject
   public PlanningActivity(PlanningView view) {
      this.view = view;
   }

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      view.setPresenter(this);
      view.bind();
      panel.setWidget(view.asWidget());
   }

   @Override
   public void onStop() {
      view.unbind();
   }
}
