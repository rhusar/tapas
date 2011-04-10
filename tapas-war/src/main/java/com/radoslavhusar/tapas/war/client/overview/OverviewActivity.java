package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;

public class OverviewActivity extends AbstractActivity implements OverviewPresenter {

   private final OverviewView view;
   private final TaskResourceServiceAsync service;
   private final ClientState client;

   @Inject
   public OverviewActivity(OverviewView view, ClientState client, TaskResourceServiceAsync service) {
      this.view = view;
      this.client = client;
      this.service = service;
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
