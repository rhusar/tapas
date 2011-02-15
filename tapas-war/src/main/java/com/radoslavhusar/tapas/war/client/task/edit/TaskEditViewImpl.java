package com.radoslavhusar.tapas.war.client.task.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Employee;
import com.radoslavhusar.tapas.war.client.app.HelloMVP;
import com.radoslavhusar.tapas.war.shared.services.MyResourceServiceAsync;
import java.util.List;

public class TaskEditViewImpl extends ResizeComposite implements TaskEditView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

//   private MyResourceService rs; // = GWT.create(MyResourceService.class);
   interface Binder extends UiBinder<Widget, TaskEditViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField
   TextBox box;
//   @UiField
//   CheckBox white;
   @UiField
   Anchor cancel;
   @UiField(provided = true)
   SuggestBox person;
   private MultiWordSuggestOracle peopleList = new MultiWordSuggestOracle();

   @Inject
   public TaskEditViewImpl(MyResourceServiceAsync rs) {
      //    rs = GWT.create(MyResourceService.class);

      person = new SuggestBox(peopleList);
      initWidget(binder.createAndBindUi(this));
      System.out.println(rs);




      rs.getResourcesForProject(null, new AsyncCallback<List<Employee>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Employee> result) {
            for (Employee e : result) {
               peopleList.add(e.getEmployeeName());
            }
         }
      });




   }

   @Override
   public void bind() {
      menu.add(HelloMVP.getInjector().getMenuView());
      status.add(HelloMVP.getInjector().getStatusView());
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

   @UiHandler("cancel")
   void cancelAndReturn(ClickEvent event) {
      presenter.goTo();
   }
}
