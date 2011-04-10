package com.radoslavhusar.tapas.war.client.planning;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;

public class PlanningViewImpl extends ResizeComposite implements PlanningView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);
   private final ClientState client;
   private final TaskResourceServiceAsync service;

   interface Binder extends UiBinder<Widget, PlanningViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField
   VerticalPanel content;
   private Project project;

   @Inject
   public PlanningViewImpl(ClientState client, TaskResourceServiceAsync service) {
      this.client = client;
      this.service = service;

      initWidget(binder.createAndBindUi(this));
      content.setSpacing(5);

      // TODO: make this View a singleton, the calculations are quite complex on the server side.
      GWT.log("New PlanningViewImpl created.");
   }

   // UI routines
   @Override
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());

      //content.add(new heading("a"));
      //content.add(new HTML("<h1>Loading..</h1>"));
      /*HorizontalPanel horizon = new HorizontalPanel();
      horizon.setWidth("100%");
      content.add(horizon);
      VerticalPanel left = new VerticalPanel();
      left.setWidth("50%");
      left.add(new HTML("<h1>Loading..XXXXXXXXXXXXXXX</h1>"));
      horizon.add(left);
      VerticalPanel right = new VerticalPanel();
      horizon.add(right);
      right.setWidth("50%");
      right.add(new HTML("<h1>Loading..</h1>"));*/

      // Call the services
      if (client.getProject() == null) {
         // TODO: This is done now in the ClientState as well.
         service.findProject(client.getProjectId(), new AsyncCallback<Project>() {

            @Override
            public void onFailure(Throwable caught) {
               throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(Project result) {
               project = result;
               toSyncRender();
            }
         });
      } else {
         toSyncRender();
      }
      /*
      if (client.getProject() == null) {
      service.(client.getProjectId(), new AsyncCallback<Project>() {
      
      @Override
      public void onFailure(Throwable caught) {
      throw new UnsupportedOperationException("Not supported yet.");
      }
      
      @Override
      public void onSuccess(Project result) {
      project = result;
      toSyncRender();
      }
      });
      } else {
      toSyncRender();
      }*/



   }
   private int toSync;

   private void toSyncRender() {
      toSync--;

      if (toSync == 0) {
         render();
      }
   }

   public void render() {
      content.clear();

      for (ProjectPhase pp : client.getProject().getPhases()) {
      }
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
