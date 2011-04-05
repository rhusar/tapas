package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.radoslavhusar.tapas.war.client.app.Application;

public class TasksActivity extends AbstractActivity implements TasksView.Presenter {

   TasksViewImpl view;

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      view = Application.getInjector().getTaskListView();
      view.setPresenter(this);
      view.bind();

      panel.setWidget(view.asWidget());
   }

   @Override
   public void onStop() {
      view.unbind();
   }
}
