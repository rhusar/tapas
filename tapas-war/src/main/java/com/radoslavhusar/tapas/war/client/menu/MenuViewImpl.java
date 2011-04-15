package com.radoslavhusar.tapas.war.client.menu;

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
import com.radoslavhusar.tapas.war.client.state.ClientState;
import com.radoslavhusar.tapas.war.client.overview.OverviewPlace;
import com.radoslavhusar.tapas.war.client.planning.PlanningPlace;
import com.radoslavhusar.tapas.war.client.projects.ProjectsPlace;
import com.radoslavhusar.tapas.war.client.resources.ResourcesPlace;
import com.radoslavhusar.tapas.war.client.tasks.TasksPlace;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.List;

public class MenuViewImpl extends Composite implements MenuView {

   private static Binder binder = GWT.create(Binder.class);
   private Presenter presenter;
   private final ClientState client;

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
   public MenuViewImpl(final MenuPresenter presenter, final TaskResourceServiceAsync res, final ClientState client) {
      GWT.log("MenuViewImpl created.");
      this.client = client;
      this.presenter = presenter;

      initWidget(binder.createAndBindUi(this));

      // Set loading title (woraround project since everything is data-binded)
      Project dummy = new Project();
      dummy.setName("Loading...");
      projectSwitch.setValue(dummy);

      // Workaround for http://code.google.com/p/google-web-toolkit/issues/detail?id=6112 - disable the box
      DOM.setElementPropertyBoolean(projectSwitch.getElement(), "disabled", true);


      res.findAllProjects(new AsyncCallback<List<Project>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Project> result) {
            // Find which one was selected
            Long current = client.getProjectId();
            if (current != null) {
               for (Project p : result) {
                  if (p.getId().equals(current)) {
                     projectSwitch.setValue(p);
                     break;
                  }
               }
            } else {
               // none was selected... get the last one then 
               // TODO this is not very happy-needs a loading screen at least
               //Project p = result.get(result.size() - 1);
               Project dummy = new Project();
               dummy.setName("");
               projectSwitch.setValue(dummy);
               //projectSwitch.setValue(p);
               //Application.getInjector().getPlaceController().goTo(new OverviewPlace(p.getId()));
            }

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

   @UiHandler("projects")
   void navigateProjects(ClickEvent event) {
      Application.getInjector().getPlaceController().goTo(new ProjectsPlace());
   }

   @UiHandler("overview")
   void navigateOverview(ClickEvent event) {
      Application.getInjector().getPlaceController().goTo(new OverviewPlace(client.getProjectId()));
   }

   @UiHandler("tasks")
   void navigateTasks(ClickEvent event) {
      Application.getInjector().getPlaceController().goTo(new TasksPlace(client.getProjectId()));
   }

   @UiHandler("resources")
   void navigateResources(ClickEvent event) {
      Application.getInjector().getPlaceController().goTo(new ResourcesPlace(client.getProjectId()));
   }

   @UiHandler("planning")
   void navigatePlanning(ClickEvent event) {
      Application.getInjector().getPlaceController().goTo(new PlanningPlace(client.getProjectId()));
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
