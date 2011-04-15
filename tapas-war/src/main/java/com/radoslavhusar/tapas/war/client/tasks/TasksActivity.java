package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.war.client.state.ClientState;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.Set;

public class TasksActivity extends AbstractActivity implements TasksView.Presenter {

   TasksView view;
   private final ClientState client;
   private final TaskResourceServiceAsync service;

   @Inject
   public TasksActivity(ClientState client, TaskResourceServiceAsync service, TasksView view) {
      this.client = client;
      this.service = service;
      this.view = view;
   }

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      // view = Application.getInjector().getTaskListView();
      view.setPresenter(this);
      view.bind();
      panel.setWidget(view.asWidget());
   }

   @Override
   public void onStop() {
      view.unbind();
   }

   @Override
   public void doSaveTasks(final Set<Task> changed) {

      for (final Task task : changed) {

         service.editTask(task, new AsyncCallback<Task>() {

            @Override
            public void onFailure(Throwable caught) {
               GWT.log("Saving tasks failed: ", caught);

               // Add it back to unsaved
               changed.add(task);
            }

            @Override
            public void onSuccess(Task result) {
               GWT.log("Task saved! New ID is: " + result.getId());
               task.setId(result.getId());

               // Now refresh the table
               // TODO: dont make this so often!
               view.redrawTasksTable();
            }
         });
      }

      // All tasks are set to be persisted
      changed.clear();
   }
}
