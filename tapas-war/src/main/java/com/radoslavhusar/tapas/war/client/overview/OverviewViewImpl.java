package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
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
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.BarChart.Options;
import com.google.gwt.visualization.client.visualizations.BarChart;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.ejb.stats.PhaseStatsEntry;
import com.radoslavhusar.tapas.ejb.stats.ProjectStats;
import com.radoslavhusar.tapas.ejb.stats.ResourcePhaseStatsEntry;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.state.ClientState;
import com.radoslavhusar.tapas.war.client.app.Constants;
import com.radoslavhusar.tapas.war.client.state.event.DataReadyEvent;
import com.radoslavhusar.tapas.war.client.state.event.DataReadyEventHandler;
import com.radoslavhusar.tapas.war.client.state.event.DataType;
import com.radoslavhusar.tapas.war.client.ui.NullableDatePickerCell;
import com.radoslavhusar.tapas.war.client.ui.SafeHtmlCellContent;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class OverviewViewImpl extends ResizeComposite implements OverviewView {

   private OverviewPresenter presenter;
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
   /*@UiField
   Anchor phaseRemove;*/
   @UiField
   Label propToday;
   @UiField
   DatePicker propStart;
   @UiField
   DatePicker propTarget;
   @UiField
   Label propDays;
   Project project;
   DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
   @UiField
   VerticalPanel vpanel;
   private ProjectStats stats;
   @UiField
   VerticalPanel piePanel;
   @UiField
   Label piePercent;
   @UiField
   Label pieDays;

   @Inject
   public OverviewViewImpl(ClientState client, TaskResourceServiceAsync service, EventBus bus) {
      this.client = client;
      this.service = service;
      this.bus = bus;

      phases = new CellTable<ProjectPhase>();

      // ID
      /*
      TextColumn<ProjectPhase> idCol = new TextColumn<ProjectPhase>() {
      
      @Override
      public String getValue(ProjectPhase phase) {
      return phase.getId() == null ? "" : phase.getId().toString();
      }
      };
      
      phases.addColumn(idCol, "DebugID");
      phases.setColumnWidth(idCol, 2, Unit.EM);
       */

      // Name
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
            /*if (stats.getProjection() == null || stats.getProjection().get(phase.getId()) == null) {
            return null;
            }*/

            PhaseStatsEntry phaseStats = stats.getProjection().get(phase.getId());

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
               PhaseStatsEntry stat = stats.getProjection().get(phase.getId());

               if (stat == null) {
                  return new SafeHtmlCellContent("Internal Error!");
               }

               // Now compare DUE and TARGET time
               if (stat.getProjectedEnd().before(phase.getTargetted())) {
                  // Happy: its all goodies.
                  return new SafeHtmlCellContent("<span style='color:green;'>On time.</span>");
               } else {
                  // its after :-(
                  return new SafeHtmlCellContent("<span style='color:red;'>Slipping</br>by " + stat.getSlip() + " days</span>");
               }
            } else {
               // Completed already.

               if (phase.getEnded().before(phase.getTargetted()) || phase.getEnded().equals(phase.getTargetted())) {
                  return new SafeHtmlCellContent("<span style='color:green;'>Completed</br>on time</span>");
               } else {
                  return new SafeHtmlCellContent("<span style='color:green;'>Completed</br>with slip</span>");
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

      /** TODO: Template this **/
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
   }

   // UI routines
   @Override
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
               /*
               Collections.sort(client.getProject().getPhases());
               for (ProjectPhase pp : client.getProject().getPhases()) {
                  if (pp.getEnded() != null) {
                     client.prepareResourceStats(pp.getId());
                  }
               }*/
               client.prepareResourceStats(client.getProjectId());
            } else {
               renderChartNow();
            }

            // Pie chard
            if (client.getProjectStats() == null) {
               bus.addHandler(DataReadyEvent.TYPE, new DataReadyEventHandler() {

                  @Override
                  public void onDataReady(DataReadyEvent event) {
                     if (event.getType().equals(DataType.PROJECT_STATS)) {
                        stats = client.getProjectStats();
                        renderPie();
                        renderPhasesTable();
                     }
                  }
               });
               client.prepareProjectStats();
            } else {
               renderPie();
            }
         }
      };


      // Load the visualization api, passing the onLoadCallback to be called when loading is done.
      VisualizationUtils.loadVisualizationApi(chartCallback, BarChart.PACKAGE);

      project = client.getProject();

      if (project == null) {
         bus.addHandler(DataReadyEvent.TYPE, new DataReadyEventHandler() {

            @Override
            public void onDataReady(DataReadyEvent event) {
               if (event.getType().equals(DataType.PROJECT)) {
                  project = client.getProject();
                  renderProjectInfo();
               }
            }
         });
         client.prepareProject();
      } else {
         renderProjectInfo();
      }
   }

   private void renderProjectInfo() {
      // Set the properties
      propToday.setText(dateFormat.format(new Date()));
      //propStart.setText(dateFormat.format(project.getStartDate()));
      //propTarget.setText(dateFormat.format(project.getTargetDate()));
      propStart.setValue(project.getStartDate());
      propStart.addValueChangeHandler(new ValueChangeHandler<Date>() {

         @Override
         public void onValueChange(ValueChangeEvent<Date> event) {
            project.setStartDate(event.getValue());
         }
      });
      propTarget.setValue(project.getTargetDate());
      propTarget.addValueChangeHandler(new ValueChangeHandler<Date>() {

         @Override
         public void onValueChange(ValueChangeEvent<Date> event) {
            project.setTargetDate(event.getValue());
         }
      });
      double days = Math.round(project.getTargetDate().getTime() - (new Date()).getTime());
      days /= 86400000;
      propDays.setText("" + NumberFormat.getDecimalFormat().format(days));

      // Set the phases
      projectName.setText(project.getName());

      renderPhasesTable();
   }

   /**
    * This needs both stats data and project data to be loaded.
    */
   private void renderPhasesTable() {
      if (project.getPhases() == null || project == null || client.getProjectStats() == null) {
         // Do nothing... wait for another sync
         // phases.setRowCount(0);
      } else {
         Collections.sort(project.getPhases());
         phases.setRowData(project.getPhases());
      }
   }

   @Override
   public void unbind() {
      menu.clear();
      status.clear();
   }

   @Override
   public void setPresenter(OverviewPresenter presenter) {
      this.presenter = presenter;
   }

   /*@UiHandler("phaseRemove")
   public void removePhase(ClickEvent event) {
      ProjectPhase deletePhase = ((SingleSelectionModel<ProjectPhase>) phases.getSelectionModel()).getSelectedObject();
      if (deletePhase.getId() == 0) {
         project.getPhases().remove(deletePhase);
         phases.setRowData(project.getPhases());
         Window.alert("Removed '" + deletePhase.getName() + "'.");
      } else {
         Window.alert("Do you want to remove '" + deletePhase.getName() + "' phase? Not supported yet.");
      }
   }*/

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

   private Options getOptions(long phaseId) {
      Options options = Options.create();
      options.setWidth(600);
      options.setHeight(45 + client.getResourceStats().get(phaseId).size() * 60);
      options.set3D(true);

      for (ProjectPhase pp : client.getProject().getPhases()) {
         if (pp.getId().equals(phaseId)) {
            //options.setTitle(pp.getName() + " Phase Progress");
            break;
         }
      }

      return options;
   }

   private DataTable getData(long phaseId) {

      DataTable data = DataTable.create();

      data.addColumn(ColumnType.STRING, "Resource");
      data.addColumn(ColumnType.NUMBER, "Allocated");
      data.addColumn(ColumnType.NUMBER, "Completed");

      data.addRows(client.getResourceStats().get(phaseId).size());

      int i = 0;

      for (ResourcePhaseStatsEntry rs : client.getResourceStats().get(phaseId)) {
         data.setValue(i, 0, rs.getResource() == null ? "Unassigned" : rs.getResource().getName());
         data.setValue(i, 1, rs.getAllocated());
         data.setValue(i, 2, rs.getCompleted());

         i++;
      }

      return data;
   }

   private void renderChartNow() {
      vpanel.clear();

      for (ProjectPhase pp : client.getProject().getPhases()) {
         long phaseId = pp.getId();

         if (pp.getEnded() != null) {
            // it already ended, ignore it
            continue;
         }

         if (!client.getResourceStats().containsKey(pp.getId())) {
            vpanel.add(new Label("No data for " + pp.getName() + " loaded."));
            continue;
         }

         if (client.getResourceStats().isEmpty()) {
            vpanel.add(new Label("No allocations for " + pp.getName() + "."));
            continue;
         }

         vpanel.add(new Label("" + pp.getName()));

         // Create a pie chart visualization.
         BarChart chart = new BarChart(getData(phaseId), getOptions(phaseId));

         vpanel.add(chart);
      }
   }

   private void renderPie() {
      // Data starts
      ProjectStats rs = client.getProjectStats();

      // text next to the pie
      pieDays.setText(NumberFormat.getFormat(Constants.ALLOC_FORMAT).format(rs.getRemaining() / rs.getMandayRate()) + "");
      piePercent.setText(NumberFormat.getPercentFormat().format(rs.getCompleted() / rs.getAllocated()));

      // opts
      PieChart.Options pieOpts = PieChart.Options.create();
      pieOpts.setWidth(300);
      pieOpts.setHeight(300);
      pieOpts.set3D(true);
      pieOpts.setTitle("Project Progress");

      // data
      DataTable data = DataTable.create();

      data.addColumn(ColumnType.STRING, "Completion");
      data.addColumn(ColumnType.NUMBER, "Mandays");
      data.addRows(2);

      data.setValue(0, 0, "Completed");
      data.setValue(0, 1, rs.getCompleted());

      data.setValue(1, 0, "Remaining");
      data.setValue(1, 1, rs.getRemaining());

      PieChart pie = new PieChart(data, pieOpts);

      piePanel.clear();
      piePanel.add(pie);
   }
}
