package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.BarChart.Options;
import com.google.gwt.visualization.client.visualizations.BarChart;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.ejb.stats.ProjectPhaseStats;
import com.radoslavhusar.tapas.ejb.stats.ProjectStats;
import com.radoslavhusar.tapas.ejb.stats.ResourceStats;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.client.event.DataReadyEvent;
import com.radoslavhusar.tapas.war.client.event.DataReadyEventHandler;
import com.radoslavhusar.tapas.war.client.event.DataType;
import com.radoslavhusar.tapas.war.client.ui.NullableDatePickerCell;
import com.radoslavhusar.tapas.war.client.ui.SafeHtmlCellContent;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class OverviewViewImpl extends ResizeComposite implements OverviewView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);
   private final ClientState client;
   private final TaskResourceServiceAsync service;
   private final EventBus bus;

   interface Binder extends UiBinder<Widget, OverviewViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField(provided = true)
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
   Project project;
   DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
   @UiField
   VerticalPanel vpanel;
   private ProjectStats stats;

   @Inject
   public OverviewViewImpl(ClientState client, TaskResourceServiceAsync service, EventBus bus) {
      this.client = client;
      this.service = service;
      this.bus = bus;

      phases = new CellTable<ProjectPhase>();

      // ID
      TextColumn<ProjectPhase> idCol = new TextColumn<ProjectPhase>() {

         @Override
         public String getValue(ProjectPhase phase) {
            return phase.getId() == null ? "" : phase.getId().toString();
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

      /*
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
       */

      // Target Date - also sorts like this
      Cell targetDatePickCell = new NullableDatePickerCell(dateFormat);
      Column<ProjectPhase, Date> targetDateCol = new Column<ProjectPhase, Date>(targetDatePickCell) {

         @Override
         public Date getValue(ProjectPhase phase) {
            return phase.getTargetted();
         }
      };
      targetDateCol.setFieldUpdater(new FieldUpdater<ProjectPhase, Date>() {

         @Override
         public void update(int index, ProjectPhase object, Date value) {
            object.setTargetted(value);
         }
      });
      phases.addColumn(targetDateCol, "Target Date");

      // End Date
      Cell endDatePickCell = new NullableDatePickerCell(dateFormat);
      Column<ProjectPhase, Date> endDateCol = new Column<ProjectPhase, Date>(endDatePickCell) {

         @Override
         public Date getValue(ProjectPhase phase) {
            return phase.getEnded();
         }
      };
      endDateCol.setFieldUpdater(new FieldUpdater<ProjectPhase, Date>() {

         @Override
         public void update(int index, ProjectPhase object, Date value) {
            object.setEnded(value);

            // Workaround for nice updating after edit.
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

               @Override
               public void execute() {
                  phases.redraw();
               }
            });
         }
      });
      phases.addColumn(endDateCol, "Actual End");

      // Estimated Date
      Cell estimatedDateCell = new DateCell(dateFormat);
      Column<ProjectPhase, Date> estimateCol = new Column<ProjectPhase, Date>(estimatedDateCell) {

         @Override
         public Date getValue(ProjectPhase phase) {
            ProjectPhaseStats phaseStats = stats.getProjection().get(phase.getId());

            if (phaseStats != null) {
               return phaseStats.getProjectedEnd();
            } else {
               // its completed already... return nothing
               return null;
            }
         }
      };
      // There is no updater!
      phases.addColumn(estimateCol, "Estimated");
      // Estimated Date

      Cell ontimeCell = new SafeHtmlCell();
      Column<ProjectPhase, SafeHtml> ontimeCol = new Column<ProjectPhase, SafeHtml>(ontimeCell) {

         @Override
         public SafeHtml getValue(ProjectPhase phase) {
            if (phase.getEnded() == null) {
               // Not completed yet
               ProjectPhaseStats stat = stats.getProjection().get(phase.getId());

               // Now compare DUE and TARGET time
               if (stat.getProjectedEnd().before(phase.getTargetted())) {
                  // Happy: its all goodies.
                  return new SafeHtmlCellContent("<span style='color:green;'>On time.</span>");
               } else {
                  // its after :-(
                  return new SafeHtmlCellContent("<span style='color:red;'>Slipping by " + stat.getSlip() + " days!</span>");
               }
            } else {
               // Completed already.

               if (phase.getEnded().before(phase.getTargetted()) || phase.getEnded().equals(phase.getTargetted())) {
                  return new SafeHtmlCellContent("<span style='color:green;'>Completed on time.</span>");
               } else {
                  return new SafeHtmlCellContent("<span style='color:green;'>Completed with slip.</span>");
               }
            }
         }
      };
      // There is no updater!
      phases.addColumn(ontimeCol, "");


      // Add a selection model to handle user selection.
      final SingleSelectionModel<ProjectPhase> selectionModel = new SingleSelectionModel<ProjectPhase>();
      phases.setSelectionModel(selectionModel);
      phases.setPageSize(Integer.MAX_VALUE - 1);

      initWidget(binder.createAndBindUi(this));
   }

   // UI routines
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());


      // Create a callback to be called when the visualization API has been loaded - fake runnable.
      Runnable chartCallback = new Runnable() {

         @Override
         public void run() {
            if (client.getResourceStats() == null) {
               bus.addHandler(DataReadyEvent.TYPE, new DataReadyEventHandler() {

                  @Override
                  public void onDataReady(DataReadyEvent event) {
                     if (event.getType().equals(DataType.RESOURCE_STATS)) {
                        renderChartNow();
                     }
                  }
               });
               Collections.sort(client.getProject().getPhases());
               for (ProjectPhase pp : client.getProject().getPhases()) {
                  if (pp.getEnded() != null) {
                     client.prepareResourceStats(pp.getId());
                     return;
                  }
               }
            } else {
               renderChartNow();
            }
         }
      };

      // Load the visualization api, passing the onLoadCallback to be called when loading is done.
      VisualizationUtils.loadVisualizationApi(chartCallback, BarChart.PACKAGE);

      project = client.getProject();

      service.tallyProjectStats(client.getProjectId(), new AsyncCallback<ProjectStats>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(ProjectStats result) {
            // get the result somewhere
            stats = result;

            phases.setRowData(project.getPhases());
         }
      });

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
      projectName.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
            simplePopup.setWidth("150px");
            final TextBox box = new TextBox();
            box.setText(projectName.getText());
            box.addKeyUpHandler(new KeyUpHandler() {

               @Override
               public void onKeyUp(KeyUpEvent event) {
                  if (event.getNativeKeyCode() == 13) {
                     project.setName(box.getText());
                     projectName.setText(box.getText());
                     simplePopup.hide();
                  }
               }
            });
            simplePopup.setWidget(box);
            simplePopup.show();
            box.setFocus(true);
            Widget source = (Widget) event.getSource();
            int left = source.getAbsoluteLeft() + 10;
            int top = source.getAbsoluteTop() + 10;
            simplePopup.setPopupPosition(left, top);
         }
      });

      if (project.getPhases() == null) {
         phases.setRowCount(0);
      } else {
         Collections.sort(project.getPhases());
         // moved up: phases.setRowData(project.getPhases());
      }
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }

   @UiHandler("phaseRemove")
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
      newPhase.setTargetted(new Date());
      newPhase.setEnded(null);
      newPhase.setProject(project);
      if (project.getPhases() == null) {
         project.setPhases(new ArrayList<ProjectPhase>());
      }
      project.getPhases().add(newPhase);
      phases.setRowData(project.getPhases());
   }

   @UiHandler("phaseSave")
   public void phaseSaveClick(ClickEvent event) {
      service.editProject(project, new AsyncCallback<Void>() {

         @Override
         public void onFailure(Throwable caught) {
            GWT.log("Failed saving phases.");
         }

         @Override
         public void onSuccess(Void result) {
            GWT.log("Saved phases OK.");
         }
      });
   }

   private Options getOptions() {
      Options options = Options.create();
      options.setWidth(600);
      options.setHeight(client.getResourceStats().size()*80);
      options.set3D(true);
      options.setTitle("Phase Progress");
      return options;
   }

   private DataTable getData() {

      DataTable data = DataTable.create();

      data.addColumn(ColumnType.STRING, "Resource");
      //data.addColumn(ColumnType.STRING, "Completed");
      data.addColumn(ColumnType.NUMBER, "Allocated");
      data.addColumn(ColumnType.NUMBER, "Completed");
      

      data.addRows(client.getResourceStats().size());

      int i = 0;
      for (ResourceStats rs : client.getResourceStats()) {

         data.setValue(i, 0, rs.getResource().getName());
         //data.setValue(0, 1, "Mountain View");
         data.setValue(i, 1, rs.getAllocated());
         data.setValue(i, 2, rs.getCompleted());
         

         i++;
      }


      /*data.setValue(0, 0, "Work");
      data.setValue(0, 1, "Mountain View");
      data.setValue(0, 2, 10);*/


      /*  data.addColumn(ColumnType.STRING, "Task");
      data.addColumn(ColumnType.STRING, "Location");
      data.addColumn(ColumnType.NUMBER, "Hours per Day");
      data.addRows(3);
      data.setValue(0, 0, "Work");
      data.setValue(0, 1, "Mountain View");
      data.setValue(0, 2, 10);
      data.setValue(1, 0, "Commute");
      data.setValue(1, 1, "Route 17");
      data.setValue(1, 2, 4);
      data.setValue(2, 0, "Sleep");
      data.setValue(2, 1, "Santa Cruz");
      data.setValue(2, 2, 10);*/
      return data;
   }

   private void renderChartNow() {
      // Create a pie chart visualization.
      BarChart chart = new BarChart(getData(), getOptions());

      //pie.addSelectHandler(createSelectHandler(pie));
      vpanel.clear();
      vpanel.add(chart);
   }
}
