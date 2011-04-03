package com.radoslavhusar.tapas.war.client.projects;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Trait;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;

public class ProjectsActivity extends AbstractActivity implements ProjectsView.Presenter {

   ProjectsView view;
   private final TaskResourceServiceAsync service;

   @Inject
   public ProjectsActivity(TaskResourceServiceAsync service) {
      this.service = service;
   }

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      view = Application.getInjector().getProjectsView();
      view.setPresenter(this);
      view.bind();
      panel.setWidget(view.asWidget());
   }

   @Override
   public void onStop() {
      view.unbind();
   }

   @Override
   public void saveTrait(Trait trait) {
      service.createTrait(trait, new AsyncCallback<Void>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(Void result) {
            throw new UnsupportedOperationException("Not supported yet.");
         }
      });
   }
}
