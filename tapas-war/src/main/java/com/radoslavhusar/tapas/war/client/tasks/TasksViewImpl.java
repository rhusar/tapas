package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.TaskStatus;
import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.client.app.Constants;
import com.radoslavhusar.tapas.war.client.components.DynamicSelectionCell;
import com.radoslavhusar.tapas.war.client.components.NumberEditTextCell;
import com.radoslavhusar.tapas.war.client.components.PriorityEditTextCell;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TasksViewImpl extends ResizeComposite implements TasksView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, TasksViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField(provided = true)
   CellTable tasks = new CellTable<Task>();
   @UiField
   TextBox filter;
   private ListDataProvider<Task> provider;
   private Set<Task> changed = new HashSet<Task>();
   SimplePager filterPager;
   private ClientState client;
   private DynamicSelectionCell resourceCell;

   @Inject
   public TasksViewImpl(final ClientState client) {
      this.client = client;

      provider = new ListDataProvider<Task>() {

         // FIXME: Work around to display data onRangeChanged.
         @Override
         public void refresh() {
            super.refresh();
            this.onRangeChanged(tasks);
         }

         @Override
         protected void onRangeChanged(HasData<Task> display) {
            String filterBy = filter.getText();

            if (filterBy.isEmpty()) {
               display.setVisibleRange(0, this.getList().size());
               display.setRowData(0, this.getList());
            } else {
               ArrayList<Task> filteredlist = new ArrayList<Task>();

               for (Task task : this.getList()) {
                  if ((task.getSummary() != null && task.getSummary().contains(filterBy))
                          || (task.getName() != null && task.getName().contains(filterBy))) {
                     filteredlist.add(task);
                  }
               }

               display.setRowCount(filteredlist.size());
               display.setRowData(0, filteredlist);
               GWT.log("Filtered by " + filter.getText() + ".");
            }
         }
      };
      tasks = new CellTable<Task>(provider);

      initWidget(binder.createAndBindUi(this));

      filter.addKeyUpHandler(new KeyUpHandler() {

         @Override
         public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == 27) {
               // if ESC is pressed, clear the filter
               filter.setText("");
            }

            // Do the filter!
            provider.refresh();
         }
      });

      tasks.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

      // ID (readonly)
      TextColumn<Task> idCol = new TextColumn<Task>() {

         @Override
         public String getValue(Task task) {
            return "" + (task.getId() == 0 ? "" : task.getId());
         }
      };
      tasks.addColumn(idCol, "ID");
      tasks.setColumnWidth(idCol, 2, Unit.EM);

      // Unified ID
      Cell unifIdCell = new EditTextCell();
      Column<Task, String> unifiedIdCol = new Column<Task, String>(unifIdCell) {

         @Override
         public String getValue(Task task) {
            return "" + (task.getUnifiedId() == null ? "" : task.getUnifiedId());
         }
      };
      unifiedIdCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            changed.add(object);

            object.setUnifiedId(value);
         }
      });
      tasks.addColumn(unifiedIdCol, "UID");
      tasks.setColumnWidth(unifiedIdCol, 2, Unit.EM);

      // Priority
      Cell prioEditCell = new PriorityEditTextCell();
      Column<Task, String> prioCol = new Column<Task, String>(prioEditCell) {

         @Override
         public String getValue(Task task) {
            return task.getPriority() == null ? "TBD" : "P" + task.getPriority();
         }
      };
      prioCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            if (value.equalsIgnoreCase("TBD")) {
               object.setPriority(null);
               return;
            }
            String input = value.replaceAll("P", "").replaceAll("p", "");
            Byte num = 1;

            try {
               num = Byte.parseByte(input);
               if (!(num >= 1 && num <= 3)) {
                  throw new NumberFormatException();
               }
            } catch (NumberFormatException nfo) {
               Window.alert("Wrong priority number. Please correct.");
               tasks.redraw();
            }

            object.setPriority(num);
            tasks.redraw();
         }
      });
      tasks.addColumn(prioCol, "Prio");
      tasks.setColumnWidth(prioCol, 1, Unit.EM);

      // Status
      List<String> statusOptions = new ArrayList<String>();
      statusOptions.add(""); // The null value
      for (TaskStatus ts : TaskStatus.values()) {
         statusOptions.add(Task.formatState(ts));
      }
      SelectionCell statusCell = new SelectionCell(statusOptions);
      Column<Task, String> statusCol = new Column<Task, String>(statusCell) {

         @Override
         public String getValue(Task task) {
            return Task.formatState(task.getStatus());
         }
      };
      statusCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            changed.add(object);

            if (value.isEmpty()) {
               object.setStatus(null);
               return;
            }

            for (TaskStatus ts : TaskStatus.values()) {
               if (Task.formatState(ts).equals(value)) {
                  object.setStatus(ts);
                  return;
               }
            }
         }
      });
      tasks.addColumn(statusCol, "Status");
      tasks.setColumnWidth(statusCol, 10, Unit.EM);

      // Name
      Cell<String> nameCell = new EditTextCell();
      Column<Task, String> nameCol = new Column<Task, String>(nameCell) {

         @Override
         public String getValue(Task task) {
            return "" + task.getName();
         }
      };
      nameCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            changed.add(object);

            object.setName(value);
         }
      });
      tasks.addColumn(nameCol, "Name");

      // Resource
      List<String> opts = new ArrayList<String>();
      opts.add(Constants.UNASSIGNED);
      resourceCell = new DynamicSelectionCell(opts); // Needs to be fed data before data display - in bind().

      Column<Task, String> resourceCol = new Column<Task, String>(resourceCell) {

         @Override
         public String getValue(Task task) {
            return task.getResource() == null ? Constants.UNASSIGNED : task.getResource().getName();
         }
      };
      resourceCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            for (Resource r : client.getResources()) {
               if (value.equals(r.getName())) {
                  object.setResource(r);
                  changed.add(object);
                  GWT.log("Updating assignee, value: " + value);
                  return;
               }
            }

         }
      });
      tasks.addColumn(resourceCol, "Resource");
      tasks.setColumnWidth(resourceCol, 10, Unit.EM);


      // Add a selection model to handle user selection.
      final SingleSelectionModel<Task> selectionModel = new SingleSelectionModel<Task>();
      tasks.setSelectionModel(selectionModel);
      selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

         @Override
         public void onSelectionChange(SelectionChangeEvent event) {
            Task selected = selectionModel.getSelectedObject();
            if (selected != null) {
               //Window.alert("You selected: " + selected.getId());
               //presenter.goToEdit("" + selected.getId());
            }
         }
      });

      // Just show all, workaround.
      tasks.setPageSize(Integer.MAX_VALUE - 1);
   }

   // UI routines
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());
      changed.clear();


      /* CLEANUP Application.getInjector().getService().findAllResourceStatsForProject(client.getProjectId(), new AsyncCallback<Map<Resource, Double[]>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         // FIXME: in one place
         @Override
         public void onSuccess(Map<Resource, Double[]> result) {
            client.setResources(result);
            for (Resource r : client.getResources().keySet()) {
               resourceCell.addOption(r.getName());
            }


            Application.getInjector().getService().findAllTasksForProject(client.getProject().getId(), new AsyncCallback<List<Task>>() {

               @Override
               public void onFailure(Throwable caught) {
                  throw new UnsupportedOperationException("Not supported yet.");
               }

               @Override
               public void onSuccess(List<Task> result) {
                  // List<Task> s = new ArrayList(result);
                  provider.setList(result);

                  // Set the total row count. This isn't strictly necessary, but it affects
                  // paging calculations, so its good habit to keep the row count up to date.
                  tasks.setRowCount(result.size(), true);

                  // Push the data into the widget.
                  tasks.setRowData(0, result);
               }
            });
         }
      });*/

      // Phases Times
      Project project = Application.getInjector().getClientState().getProject();
      if (project != null) {
         for (final ProjectPhase phase : project.getPhases()) {
            Cell<String> timeCell = new NumberEditTextCell();
            Column<Task, String> timePhaseCol = new Column<Task, String>(timeCell) {

               @Override
               public String getValue(Task object) {
                  for (TimeAllocation tta : object.getTimeAllocations()) {
                     if (tta.getPhase().getId() == phase.getId()) {
                        return "" + tta.getAllocation();
                     }
                  }
                  return "";
               }
            };
            timePhaseCol.setFieldUpdater(new FieldUpdater<Task, String>() {

               @Override
               public void update(int index, Task object, String value) {
                  // Is the number valid anyway?
                  double doubleValue;
                  try {
                     doubleValue = Double.parseDouble(value);
                  } catch (NumberFormatException nfe) {
                     GWT.log("Could not parse input value ignoring.", nfe);
                     return;
                  }

                  changed.add(object);

                  for (TimeAllocation tta : object.getTimeAllocations()) {
                     // TODO: needs comparing IDs which is safe, but should be done .equal but doesnt work
                     if (tta.getPhase().getId() == phase.getId()) {
                        tta.setAllocation(index);
                        GWT.log("Updating allocation: " + tta);
                        tasks.redraw();
                        return;
                     }
                  }

                  // No Allocation found? Create a new one
                  TimeAllocation tta = new TimeAllocation();
                  tta.setTask(object);
                  tta.setPhase(phase);
                  tta.setAllocation(doubleValue);
                  object.getTimeAllocations().add(tta);
                  GWT.log("Creating allocation: " + tta);
                  tasks.redraw();
               }
            });

            tasks.addColumn(timePhaseCol, phase.getName().substring(0, 4));
            tasks.setColumnWidth(timePhaseCol, 2, Unit.EM);
         }
      }

      // Total
      NumberCell allocNumberCell = new NumberCell(NumberFormat.getFormat(Constants.ALLOC_FORMAT));
      Column<Task, Number> timeTotalCol = new Column<Task, Number>(allocNumberCell) {

         @Override
         public Number getValue(Task task) {
            double total = 0;

            for (TimeAllocation tta : task.getTimeAllocations()) {
               total += tta.getAllocation();
            }

            return total;
         }
      };

      tasks.addColumn(timeTotalCol, "Total");
      tasks.setColumnWidth(timeTotalCol, 2, Unit.EM);
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }

   @UiHandler("addTask")
   public void addSomeTasks(ClickEvent event) {
      Task task = new Task();
      task.setTimeAllocations(new ArrayList());
      task.setProject(client.getProject());
      task.setName("New task");
      changed.add(task);
      provider.getList().add(task);
      provider.refresh();
   }

   @UiHandler("saveTasks")
   public void saveSomeTasks(ClickEvent event) {
      GWT.log("Saving " + changed.size() + " tasks: " + changed + ".");

      Application.getInjector().getService().editTasks(changed, new AsyncCallback<Void>() {

         @Override
         public void onFailure(Throwable caught) {
            GWT.log("Saving tasks failed: ", caught);
         }

         @Override
         public void onSuccess(Void result) {
            GWT.log("Tasks saved!");
            changed.clear();
         }
      });
   }
}
