package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import java.util.ArrayList;
import java.util.Date;

public class OverviewViewImpl extends ResizeComposite implements OverviewView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, OverviewViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField(provided = true)
   CellTable phases = new CellTable<ProjectPhase>();
//   ListDataProvider<ProjectPhase> phasesProvider;
   @UiField
   Label projectName;
   @UiField
   Anchor phaseAdd;
   @UiField
   Anchor phaseSave;
   @UiField
   Anchor phaseRemove;
   Project project;

   @Inject
   public OverviewViewImpl(ClientState state) {
//      phasesProvider = new ListDataProvider<ProjectPhase>();
//      phases = new CellTable<ProjectPhase>(phasesProvider);
      phases = new CellTable<ProjectPhase>();

      // Columns!
      TextColumn<ProjectPhase> idCol = new TextColumn<ProjectPhase>() {

         @Override
         public String getValue(ProjectPhase phase) {
            return "" + (phase.getId() != 0 ? phase.getId() : "Unsaved");
         }
      };

      phases.addColumn(idCol, "ID");
      phases.setColumnWidth(idCol, 2, Unit.EM);

      Cell nameCell = new EditTextCell();
      Column<ProjectPhase, String> phaseNameCol = new Column<ProjectPhase, String>(nameCell) {

         @Override
         public String getValue(ProjectPhase phase) {
            return phase.getName();
         }
      };
      phaseNameCol.setFieldUpdater(new FieldUpdater<ProjectPhase, String>() {

         @Override
         public void update(int index, ProjectPhase object, String value) {
            object.setName(value);
            //table.redraw();
         }
      });
      phases.addColumn(phaseNameCol, "Name");

      // Start Date
      DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
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
      selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

         @Override
         public void onSelectionChange(SelectionChangeEvent event) {
            ProjectPhase selected = selectionModel.getSelectedObject();
            if (selected != null) {
            }
         }
      });

      phases.setPageSize(Integer.MAX_VALUE - 1);

      initWidget(binder.createAndBindUi(this));

      phaseAdd.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            ProjectPhase newPP = new ProjectPhase();
            newPP.setName("New phase");
            newPP.setStartDate(new Date());
            newPP.setEndDate(new Date());
            if (project.getPhases() == null) {
               project.setPhases(new ArrayList<ProjectPhase>());
            }
            project.getPhases().add(newPP);

//            phasesProvider.setList(project.getPhases());
//            phases.setRowCount(project.getPhases().size(), true);
            phases.setRowData(project.getPhases());
         }
      });

      phaseSave.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            Application.getInjector().getMyResourceService().editProject(project, new AsyncCallback<Void>() {

               @Override
               public void onFailure(Throwable caught) {
                  GWT.log("failed saving phases");
               }

               @Override
               public void onSuccess(Void result) {
                  GWT.log("saved");
               }
            });
         }
      });
   }

   // UI routines
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());

      project = Application.getInjector().getClientState().getProject();
      if (project == null) {
         return;
      }

      projectName.setText(project.getName());
      phases.setRowData(project.getPhases() == null ? new ArrayList() : project.getPhases());
//      phasesProvider.setList(project.getPhases() == null ? new ArrayList() : project.getPhases());
//      phases.setRowCount(phasesProvider.getList().size(), true);

      /*
      Application.getInjector().getMyResourceService().findAll(new AsyncCallback<List<Task>>() {

      @Override
      public void onFailure(Throwable caught) {
      throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void onSuccess(List<Task> result) {
      //            List<Task> s = new ArrayList(result);
      phasesProvider.setList(result);

      // Set the total row count. This isn't strictly necessary, but it affects
      // paging calculations, so its good habit to keep the row count up to date.
      phasesTable.setRowCount(result.size(), true);

      // Push the data into the widget.
      phasesTable.setRowData(0, result);
      }
      });*/

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
