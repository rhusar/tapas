package com.radoslavhusar.tapas.war.client.planning;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.stats.TaskAllocationPlanMeta;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.state.ClientState;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.List;

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
   private TaskAllocationPlanMeta plan;

   @Inject
   public PlanningViewImpl(ClientState client, TaskResourceServiceAsync service) {
      this.client = client;
      this.service = service;

      initWidget(binder.createAndBindUi(this));

      content.setSpacing(5);

      // TODO: make this View a singleton, the calculations are quite complex on the server side.
      GWT.log("PlanningViewImpl created.");
   }

   // UI routines
   @Override
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());

      content.clear();
      content.add(new Label("Which tasks to plan?"));

      CheckBox assignedCB = new CheckBox("Unassigned Tasks");
      content.add(assignedCB);
      CheckBox startedCB = new CheckBox("Unstarted Tasks");
      content.add(startedCB);
      Button button = new Button("Propose plan");
      content.add(button);

      button.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {

            content.add(new Label("Generating plan..."));

            service.findAllTasksForProject(client.getProjectId(), new AsyncCallback<List<Task>>() {

               @Override
               public void onFailure(Throwable caught) {
                  throw new UnsupportedOperationException("Not supported yet.");
               }

               @Override
               public void onSuccess(List<Task> result) {
                  client.setTasks(result);

                  service.findAllResourcesForProject(client.getProjectId(), new AsyncCallback<List<Resource>>() {

                     @Override
                     public void onFailure(Throwable caught) {
                        throw new UnsupportedOperationException("Not supported yet.");
                     }

                     @Override
                     public void onSuccess(List<Resource> result) {
                        client.setResources(result);

                        service.predictAllocation(client.getProjectId(), new AsyncCallback<TaskAllocationPlanMeta>() {

                           @Override
                           public void onFailure(Throwable caught) {
                              throw new UnsupportedOperationException("Not supported yet.");
                           }

                           @Override
                           public void onSuccess(TaskAllocationPlanMeta result) {
                              plan = result;
                              render();
                           }
                        });
                     }
                  });
               }
            });
         }
      });

   }

   private void render() {
      content.clear();

      for (Task t : client.getTasks()) {
         content.add(new Label(t.getName().toString() + " assign to: " + plan.getTaskToResource().get(t.getId()).toString()));
      }
   }

   @Override
   public void unbind() {
      menu.clear();
      status.clear();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
