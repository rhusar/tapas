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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.overview.OverviewPlace;
import com.radoslavhusar.tapas.war.client.resources.ResourcesPlace;
import com.radoslavhusar.tapas.war.client.tasks.TasksPlace;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
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
   public MenuViewImpl(TaskResourceServiceAsync res) {
      this.presenter = Application.getInjector().getMenuPresenter();

      initWidget(binder.createAndBindUi(this));

      // Set loading title
      Project dummy = new Project();
      dummy.setName("Loading...");
      projectSwitch.setValue(dummy);
      // Workaround for http://code.google.com/p/google-web-toolkit/issues/detail?id=6112
      DOM.setElementPropertyBoolean(projectSwitch.getElement(), "disabled", true);


      res.findAllProjects(new AsyncCallback<List<Project>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Project> result) {
            Project defaultProject = result.get(0);
            Application.getInjector().getClientState().setProject(defaultProject);
            projectSwitch.setValue(defaultProject);
            projectSwitch.setAcceptableValues(result);
            DOM.setElementPropertyBoolean(projectSwitch.getElement(), "disabled", false);
         }
      });


      projectSwitch.addValueChangeHandler(new ValueChangeHandler<Project>() {

         @Override
         public void onValueChange(ValueChangeEvent<Project> event) {
            Project selected = event.getValue();
            presenter.switchProject(selected);
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

   @UiHandler("overview")
   void navigateOverview(ClickEvent event) {
      Application.getInjector().getPlaceController().goTo(new OverviewPlace());
   }

   @UiHandler("resources")
   void navigateResources(ClickEvent event) {
      Application.getInjector().getPlaceController().goTo(new ResourcesPlace());
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
