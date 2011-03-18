package com.radoslavhusar.tapas.war.client.task.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.war.client.app.Application;

public class TaskEditViewImpl extends ResizeComposite implements TaskEditView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);
   private Long taskId;

   interface Binder extends UiBinder<Widget, TaskEditViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField
   TextBox name;
   @UiField
   TextBox summary;
   @UiField
   Anchor cancel;
   @UiField(provided = true)
   SuggestBox person;
   private MultiWordSuggestOracle peopleList = new MultiWordSuggestOracle();
   @UiField
   Button submit;

   public TaskEditViewImpl() {
      person = new SuggestBox(peopleList);
      initWidget(binder.createAndBindUi(this));
   }

   // UI Integration Routines
   @Override
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());

//      Application.getInjector().getService().getResourcesForProject(null, new AsyncCallback<List<Employee>>() {
//
//         @Override
//         public void onFailure(Throwable caught) {
//            GWT.log("Error loading possible assignees for project.");
//         }
//
//         @Override
//         public void onSuccess(List result) {
////            for (Employee e : ((List<Employee>) result)) {
////               peopleList.add(e.getEmployeeName());
////            }
//         }
//      });
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

   @Override
   public void setTask(Long integer) {
      this.taskId = integer;

      submit.setEnabled(false);
      // Now preload the data
      Application.getInjector().getService().find(taskId, new AsyncCallback<Task>() {

         @Override
         public void onFailure(Throwable caught) {
            submit.setEnabled(false);
         }

         @Override
         public void onSuccess(Task result) {
            name.setText(result.getName());
            summary.setText(result.getSummary());
            submit.setEnabled(true);
         }
      });
      
   }

   // UI Handlers
   @UiHandler("cancel")
   void onCancel(ClickEvent event) {
      presenter.goTo();
   }

   @UiHandler("submit")
   void onSubmit(ClickEvent event) {
      // Create a task and pass to presenter
      Task task = new Task();
      task.setId(taskId);
      task.setName(name.getText());
      task.setSummary(summary.getText());
      presenter.doSubmit(task);
   }
}
