package com.radoslavhusar.tapas.war.client.projects;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.client.overview.OverviewPlace;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.Collections;
import java.util.List;

public class ProjectsViewImpl extends ResizeComposite implements ProjectsView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);
   private final ClientState client;
   private final TaskResourceServiceAsync service;

   interface Binder extends UiBinder<Widget, ProjectsViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
   @UiField
   HTMLPanel left;
   @UiField
   VerticalPanel projectsPanel;
   @UiField
   Anchor manageTraits;
   @UiField
   Anchor manageExternal;

   @Inject
   public ProjectsViewImpl(ClientState client, TaskResourceServiceAsync service) {
      this.client = client;
      this.service = service;

      initWidget(binder.createAndBindUi(this));
      GWT.log("New ProjectsViewImpl created.");
   }

   // UI routines
   @Override
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());

      projectsPanel.clear();
      projectsPanel.add(new Label("Loading Projects..."));

      service.findAllProjects(new AsyncCallback<List<Project>>() {

         @Override
         public void onFailure(Throwable caught) {
            //throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Project> result) {
            //throw new UnsupportedOperationException("Not supported yet.");
            projectsPanel.clear();

            Collections.reverse(result);

            for (final Project p : result) {

               FlowPanel fp = new FlowPanel();
               fp.setWidth("100%");
               Anchor link = new Anchor(p.getName());
               link.addClickHandler(new ClickHandler() {

                  @Override
                  public void onClick(ClickEvent event) {
                     Application.getInjector().getPlaceController().goTo(new OverviewPlace(p.getId()));
                  }
               });
               fp.add(link);
               fp.add(new InlineLabel(
                       (p.getPhases().isEmpty() ? "" : " in " + p.getPhases().get(0).getName())
                       + "" + (p.getTargetDate() == null ? "" : " due " + p.getTargetDate())));
               projectsPanel.add(fp);
            }
         }
      });
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

   @UiHandler("newProject")
   public void doNewProject(ClickEvent event) {
      /*
      // Create a dialog box and set the caption text
      final DialogBox dialogBox = new DialogBox();
      //dialogBox.ensureDebugId("cwDialogBox");
      dialogBox.setText("Start a new project...");
      
      // Create a table to layout the content
      VerticalPanel dialogContents = new VerticalPanel();
      dialogContents.setSpacing(4);
      dialogBox.setWidget(dialogContents);
      
      // Add some text to the top of the dialog
      HTML details = new HTML("Name a new project:");
      dialogContents.add(details);
      //dialogContents.setCellHorizontalAlignment(details, HasHorizontalAlignment.ALIGN_CENTER);
      
      // Add an image to the dialog
      //Image image = new Image(Showcase.images.jimmy());
      //dialogContents.add(image);
      //dialogContents.setCellHorizontalAlignment(        image, HasHorizontalAlignment.ALIGN_CENTER);
      
      // Add a close button at the bottom of the dialog
      Button closeButton = new Button("Close", new ClickHandler() {
      
      @Override
      public void onClick(ClickEvent event) {
      dialogBox.hide();
      }
      });
      dialogContents.add(closeButton);
      
      Button createButton = new Button("Close", new ClickHandler() {
      
      @Override
      public void onClick(ClickEvent event) {
      Application.getInjector().getPlaceController().goTo(null);
      }
      });
      dialogContents.add(createButton);
      
      dialogContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
      
      
      // Return the dialog box
      
      dialogBox.setGlassEnabled(true);
      dialogBox.setAnimationEnabled(true);
      
      
      // onclick
      dialogBox.center();
      dialogBox.show();
       */
   }

   @UiHandler("manageTraits")
   void showManageTraitsDialog(ClickEvent event) {
      ManageTraitsDialogBox.getDialogBox(presenter);
   }
}
