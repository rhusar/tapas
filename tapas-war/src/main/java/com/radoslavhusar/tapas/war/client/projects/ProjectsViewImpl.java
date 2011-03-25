package com.radoslavhusar.tapas.war.client.projects;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ProjectsViewImpl extends ResizeComposite implements ProjectsView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, ProjectsViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   /* @UiField(provided = true)
   CellTable phases = new CellTable<ProjectPhase>();
  @UiField
   Label projectName;
   @UiField
   Anchor phaseAdd;
   @UiField
   Anchor phaseSave;
   @UiField
   Anchor phaseRemove;
   @UiField
   Label propToday;
   @UiField
   Label propStart;
   @UiField
   Label propTarget;
   @UiField
   Label propDays;
   Project project;*/
   DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);

   @Inject
   public ProjectsViewImpl(ClientState state) {
     /* phases = new CellTable<ProjectPhase>();

      // ID
      TextColumn<ProjectPhase> idCol = new TextColumn<ProjectPhase>() {

         @Override
         public String getValue(ProjectPhase phase) {
            return "" + (phase.getId() != 0 ? phase.getId() : "New");
         }
      };

      phases.addColumn(idCol, "ID");
      phases.setColumnWidth(idCol, 2, Unit.EM);

      // Name
      Cell nameCell = new EditTextCell();
      Column<ProjectPhase, String> phaseNameCol = new Column<ProjectPhase, String>(nameCell) {

         @Override
         public String getValue(ProjectPhase phase) {
            return phase.getName();
            //+ " (" + phase.getName().substring(0, 4) + ")";
         }
      };
      phaseNameCol.setFieldUpdater(new FieldUpdater<ProjectPhase, String>() {

         @Override
         public void update(int index, ProjectPhase object, String value) {
            object.setName(value);
         }
      });
      phases.addColumn(phaseNameCol, "Name");

      // Start Date
      Cell datePickCell = new DatePickerCell(dateFormat);

      Column<ProjectPhase, Date> startDateCol = new Column<ProjectPhase, Date>(datePickCell) {

         @Override
         public Date getValue(ProjectPhase phase) {
            return phase.getStartDate();
         }
      };
      startDateCol.setFieldUpdater(new FieldUpdater<ProjectPhase, Date>() {

         @Override
         public void update(int index, ProjectPhase object, Date value) {
            object.setStartDate(value);
         }
      });
      phases.addColumn(startDateCol, "Start Date");

      // End Date
      Column<ProjectPhase, Date> endDateCol = new Column<ProjectPhase, Date>(datePickCell) {

         @Override
         public Date getValue(ProjectPhase phase) {
            return phase.getEndDate();
         }
      };
      endDateCol.setFieldUpdater(new FieldUpdater<ProjectPhase, Date>() {

         @Override
         public void update(int index, ProjectPhase object, Date value) {
            object.setEndDate(value);
         }
      });
      phases.addColumn(endDateCol, "End Date");

      // Add a selection model to handle user selection.
      final SingleSelectionModel<ProjectPhase> selectionModel = new SingleSelectionModel<ProjectPhase>();
      phases.setSelectionModel(selectionModel);
      phases.setPageSize(Integer.MAX_VALUE - 1);
*/
      initWidget(binder.createAndBindUi(this));
   }

   // UI routines
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());

      /*project = Application.getInjector().getClientState().getProject();
      if (project == null) {
         return;
      }

      // Set the properties
      propToday.setText(dateFormat.format(new Date()));
      propStart.setText(dateFormat.format(project.getStartDate()));
      propTarget.setText(dateFormat.format(project.getTargetDate()));
      double days = Math.round(project.getTargetDate().getTime() - (new Date()).getTime());
      days /= 86400000;
      propDays.setText("" + days);

      // Set the phases
      projectName.setText(project.getName());

      if (project.getPhases() == null) {
         phases.setRowCount(0);
      } else {
         Collections.sort(project.getPhases());
         phases.setRowData(project.getPhases());
      }*/
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }

  /* @UiHandler("phaseRemove")
   public void removePhase(ClickEvent event) {
      ProjectPhase deletePhase = ((SingleSelectionModel<ProjectPhase>) phases.getSelectionModel()).getSelectedObject();
      if (deletePhase.getId() == 0) {
         project.getPhases().remove(deletePhase);
         phases.setRowData(project.getPhases());
         Window.alert("Removed '" + deletePhase.getName() + "'.");
      } else {
         Window.alert("Do you want to remove '" + deletePhase.getName() + "' phase? Not supported yet.");
      }
   }

   @UiHandler("phaseAdd")
   public void phaseAddClick(ClickEvent event) {
      ProjectPhase newPhase = new ProjectPhase();
      newPhase.setName("New phase");
      newPhase.setStartDate(new Date());
      newPhase.setEndDate(new Date());
      newPhase.setProject(project);
      if (project.getPhases() == null) {
         project.setPhases(new ArrayList<ProjectPhase>());
      }
      project.getPhases().add(newPhase);
      phases.setRowData(project.getPhases());
   }

   @UiHandler("phaseSave")
   public void phaseSaveClick(ClickEvent event) {
      Application.getInjector().getService().editProject(project, new AsyncCallback<Void>() {

         @Override
         public void onFailure(Throwable caught) {
            GWT.log("Failed saving phases.");
         }

         @Override
         public void onSuccess(Void result) {
            GWT.log("Saved phases OK.");
         }
      });
   }*/
}
