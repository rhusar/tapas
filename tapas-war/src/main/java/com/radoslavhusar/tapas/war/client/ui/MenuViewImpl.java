package com.radoslavhusar.tapas.war.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.tasks.TasksPlace;
import com.radoslavhusar.tapas.war.shared.services.MyResourceServiceAsync;
import java.util.List;

public class MenuViewImpl extends Composite implements MenuView {

   private static Binder binder = GWT.create(Binder.class);
   private Presenter presenter;

   public Presenter getPresenter() {
      return presenter;
   }

   interface Binder extends UiBinder<Widget, MenuViewImpl> {
   }
   @UiField(provided = true)
   ValueListBox<Project> projectSwitch = new ValueListBox<Project>(renderer);
   static Renderer<Project> renderer = new AbstractRenderer<Project>() {

      @Override
      public String render(Project project) {
         return project.getName();
      }
   };
   @UiField
   Button tasks;

   @Inject
   public MenuViewImpl(MyResourceServiceAsync res) {
      this.presenter = Application.getInjector().getMenuPresenter();
      initWidget(binder.createAndBindUi(this));

//      Set<String> set = new HashSet<String>();
//      set.clear();
//      set.add("EAP 5.1 in Planning");
//      set.add("SOA 5.0 in Development");

      res.findAllProjects(new AsyncCallback<List<Project>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Project> result) {
            projectSwitch.setAcceptableValues(result);
         }
      });


      projectSwitch.addValueChangeHandler(new ValueChangeHandler<Project>() {

         @Override
         public void onValueChange(ValueChangeEvent<Project> event) {
            Project selected = event.getValue();
            GWT.log("Switched to project: " + selected.getName());
            Application.getInjector().getClientState().setProject(selected);
         }
      });

   }

   @UiHandler("signOutLink")
   void onSelectMeAnchorClick(ClickEvent event) {
      presenter.doAbout();
   }

   @UiHandler("tasks")
   void navigateTasks(ClickEvent event) {
      Application.getInjector().getPlaceController().goTo(new TasksPlace());
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
