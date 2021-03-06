package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
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
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.TaskStatus;
import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import com.radoslavhusar.tapas.ejb.entity.Trait;
import com.radoslavhusar.tapas.war.client.state.ClientState;
import com.radoslavhusar.tapas.war.client.app.Constants;
import com.radoslavhusar.tapas.war.client.ui.DynamicSelectionCell;
import com.radoslavhusar.tapas.war.client.ui.SizeableEditTextCell;
import com.radoslavhusar.tapas.war.client.util.UiUtil;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TasksViewImpl extends ResizeComposite implements TasksView {

   private Presenter presenter;
   private final TaskResourceServiceAsync service;
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
   @UiField
   Anchor addTask;
   @UiField
   Anchor saveTasks;

   @Inject
   public TasksViewImpl(final ClientState client, final TaskResourceServiceAsync service) {
      this.client = client;
      this.service = service;

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
                  if ( /*(task.getSummary() != null && task.getSummary().contains(filterBy))
                          ||*/(task.getName() != null && task.getName().contains(filterBy))) {
                     filteredlist.add(task);
                  }
               }

               display.setRowCount(filteredlist.size());
               display.setRowData(0, filteredlist);
               GWT.log("Filtered tasks by " + filter.getText() + ".");
            }
         }
      };
      tasks = new CellTable<Task>(provider);

      // Must be done after cell table is instantiated manuall.
      initWidget(binder.createAndBindUi(this));

      GWT.log("TasksViewImpl created.");
   }

   // UI routines
   @Override
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());

      // disable the gui
      filter.setEnabled(false);
      addTask.setEnabled(false);
      saveTasks.setEnabled(false);

      // cleanup - dont do this, this reflects the current state, you can come back from another view.
      // changed.clear();

      // TODO: Find a better way. Replace the table:
      int cols = tasks.getColumnCount();
      for (int i = 0; i < cols; i++) {
         tasks.removeColumn(0);
      }

      if (client.getProjectId() == null) {
         // no project selected, ignore that all
         return;
      }

      // fetch data from services
      toSync = 4;

      // Resources ON the project
      service.findAllResourcesForProject(client.getProjectId(), new AsyncCallback<List<Resource>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Resource> result) {
            client.setResources(result);
            toSyncRender();
         }
      });

      // Tasks
      service.findAllTasksForProject(client.getProjectId(), new AsyncCallback<List<Task>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Task> result) {
            client.setTasks(result);
            toSyncRender();
         }
      });

      // Groups
      if (client.getGroups() == null) {
         service.findAllGroups(new AsyncCallback<List<ResourceGroup>>() {

            @Override
            public void onFailure(Throwable caught) {
               throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(List<ResourceGroup> result) {
               client.setGroups(result);
               toSyncRender();
            }
         });
      } else {
         toSyncRender();
      }

      // Traits
      if (client.getTraits() == null) {
         service.findAllTraits(new AsyncCallback<List<Trait>>() {

            @Override
            public void onFailure(Throwable caught) {
               throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(List<Trait> result) {
               client.setTraits(result);
               toSyncRender();
            }
         });
      } else {
         toSyncRender();
      }
   }
   private int toSync;

   private void toSyncRender() {
      toSync--;

      if (toSync == 0) {
         renderTasks();
      }
   }

   @Override
   public void unbind() {
      menu.clear();
      status.clear();
   }

   public void renderTasks() {
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

      // Columns definitions


      /*
      // ID (readonly)
      TextColumn<Task> idCol = new TextColumn<Task>() {

         @Override
         public String getValue(Task task) {
            return task.getId() == null ? "" : task.getId().toString();
         }
      };
      tasks.addColumn(idCol, "ID");
      tasks.setColumnWidth(idCol, 2, Unit.EM);
      */


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
      List<String> prioOptions = new ArrayList<String>();
      prioOptions.add(""); // The null value = TBD
      for (int i = 1; i <= 3; i++) {
         prioOptions.add("P" + i);
      }
      Cell prioCell = new SelectionCell(prioOptions);
      Column<Task, String> prioCol = new Column<Task, String>(prioCell) {

         @Override
         public String getValue(Task task) {
            return task.getPriority() == null ? "" : "P" + task.getPriority();
         }
      };
      prioCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            if (value.isEmpty()) {
               object.setPriority(null);
               changed.add(object);
               return;
            }

            String input = value.substring(1, 2);
            Byte num;

            try {
               num = Byte.parseByte(input);
               if (!(num >= 1 && num <= 3)) {
                  throw new NumberFormatException();
               }
               object.setPriority(num);
               changed.add(object);
            } catch (NumberFormatException nfo) {
               // This cant happen now, so no need for:
               // Window.alert("Wrong priority number. Please correct.");
            }
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


      // Task Name
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

            // if it doesnt have a requirement lets do some scanning
            if (object.getRequiredTrait() == null) {
               for (Trait t : client.getTraits()) {
                  // Silly workaround for contains being case sensitive.
                  // TODO: try using pattern matching, e.g.: http://stackoverflow.com/questions/86780/is-the-contains-method-in-java-lang-string-case-sensitive
                  if (value.toLowerCase().contains(t.getName().toLowerCase())) {
                     object.setRequiredTrait(t);
                     tasks.redraw();
                     return;
                  }
               }
            }
         }
      });
      tasks.addColumn(nameCol, "Name");


      // Resource
      List<String> opts = new ArrayList<String>();
      opts.add(Constants.UNASSIGNED);
      for (Resource r : client.getResources()) {
         // Resources which are allocated on this project!
         if (r.getName() != null) {
            opts.add(r.getName());
         }
      }
      resourceCell = new DynamicSelectionCell(opts);

      Column<Task, String> resourceCol = new Column<Task, String>(resourceCell) {

         @Override
         public String getValue(Task task) {
            return (task.getResource() == null) ? Constants.UNASSIGNED : task.getResource().getName();
         }
      };
      resourceCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            changed.add(object);

            if (value.isEmpty()) {
               // Null - unassigned
               object.setResource(null);

            }
            for (Resource r : client.getResources()) {
               if (value.equals(r.getName())) {
                  object.setResource(r);
                  GWT.log("Updating assignee, value: " + value);
                  return;
               }
            }

         }
      });
      tasks.addColumn(resourceCol, "Resource");
      tasks.setColumnWidth(resourceCol, 10, Unit.EM);

      // Team - Group
      List<String> groupOptions = new ArrayList<String>();
      groupOptions.add(""); // the NULL option
      for (ResourceGroup g : client.getGroups()) {
         groupOptions.add(g.getName());
      }
      Cell groupCell = new SelectionCell(groupOptions);
      Column<Task, String> groupCol = new Column<Task, String>(groupCell) {

         @Override
         public String getValue(Task object) {
            return "" + (object.getResourceGroup() != null ? object.getResourceGroup().getName() : "");
         }
      };
      groupCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            changed.add(object);

            if (value.isEmpty()) {
               object.setResourceGroup(null);
               return;
            }
            for (ResourceGroup group : client.getGroups()) {
               if (group.getName().equals(value)) {
                  object.setResourceGroup(group);
                  return;
               }
            }
         }
      });
      // TODO: this is actually "Resource Group" but lets make it more human and call it Team as for human resources.
      tasks.addColumn(groupCol, "Team");
      tasks.setColumnWidth(groupCol, 5, Unit.EM);


      // Requirements Traits
      List<String> traitOptions = new ArrayList<String>();
      traitOptions.add(""); // the NULL option - nothing is required
      for (Trait t : client.getTraits()) {
         traitOptions.add(t.getName());
      }
      Cell traitCell = new SelectionCell(traitOptions);
      Column<Task, String> traitCol = new Column<Task, String>(traitCell) {

         @Override
         public String getValue(Task object) {
            return "" + (object.getRequiredTrait() != null ? object.getRequiredTrait().getName() : "");
         }
      };
      traitCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            changed.add(object);

            if (value.isEmpty()) {
               object.setRequiredTrait(null);
               return;
            }
            for (Trait t : client.getTraits()) {
               if (t.getName().equalsIgnoreCase(value)) {
                  object.setRequiredTrait(t);
                  return;
               }
            }
         }
      });
      tasks.addColumn(traitCol, "Requires");
      tasks.setColumnWidth(traitCol, 5, Unit.EM);


      // Phases Times
      Project project = client.getProject();
      if (project != null) {
         // Sort them before display
         Collections.sort(project.getPhases());

         for (final ProjectPhase phase : project.getPhases()) {
            Cell<String> timeCell = new SizeableEditTextCell(2);
            Column<Task, String> timePhaseCol = new Column<Task, String>(timeCell) {

               @Override
               public String getValue(Task object) {
                  for (TimeAllocation tta : object.getTimeAllocations()) {
                     if (tta.getPhase().getId().equals(phase.getId())) {
                        if (tta.getAllocated() > 0) {
                           return "" + (tta.getCompleted() <= 0 ? "" : tta.getCompleted() + "/") + tta.getAllocated();
                        } else {
                           // If its allocated to 0, then show nothing
                           return "";
                        }
                     }
                  }
                  return "";
               }
            };
            timePhaseCol.setFieldUpdater(new FieldUpdater<Task, String>() {

               @Override
               public void update(int index, Task object, String value) {
                  // One or the other, it WILL be changed.
                  changed.add(object);

                  if (value.isEmpty()) {
                     // delete the allocation...
                     for (TimeAllocation ta : object.getTimeAllocations()) {
                        if (ta.getPhase().getId().equals(phase.getId())) {
                           // Dont remove but set to 0! 
                           //object.getTimeAllocations().remove(ta);
                           ta.setAllocated(0);
                           ta.setCompleted(0);
                           return;
                        }
                     }
                  }

                  // Is the number valid anyway?
                  double assignedTime;
                  double completedTime;
                  try {
                     if (value.contains("/")) {
                        // You need to parse separately
                        String[] values = value.split("/");
                        completedTime = Double.parseDouble(values[0]);
                        assignedTime = Double.parseDouble(values[1]);
                     } else {
                        completedTime = -1; // Default to -1 if not specified - no change
                        assignedTime = Double.parseDouble(value);
                     }
                  } catch (NumberFormatException nfe) {
                     GWT.log("Could not parse input value ignoring: " + nfe.getMessage());
                     return;
                  }

                  for (TimeAllocation ta : object.getTimeAllocations()) {
                     // TODO: needs comparing IDs which is safe, but should be done .equal but doesnt work
                     if (ta.getPhase().getId().equals(phase.getId())) {
                        ta.setAllocated(assignedTime);
                        if (completedTime != -1) {
                           ta.setCompleted(completedTime);
                        }
                        GWT.log("Updating allocation: " + ta);
                        tasks.redraw();
                        return;
                     }
                  }

                  // No Allocation found? Create a new one
                  TimeAllocation ta = new TimeAllocation();
                  ta.setTask(object);
                  ta.setPhase(phase);
                  ta.setAllocated(assignedTime);
                  if (completedTime != -1) {
                     ta.setCompleted(completedTime);
                  }
                  object.getTimeAllocations().add(ta);
                  GWT.log("Creating allocation: " + ta);

                  tasks.redraw();
                  return;
               }
            });
            tasks.addColumn(timePhaseCol, UiUtil.formatPhase(phase.getName()));
            tasks.setColumnWidth(timePhaseCol, 1, Unit.EM);
         }
      }


      // Totals
      Cell allocNumberCell = new NumberCell(NumberFormat.getFormat(Constants.ALLOC_FORMAT));
      Column<Task, Number> totalTimeCol = new Column<Task, Number>(allocNumberCell) {

         @Override
         public Number getValue(Task task) {
            double total = 0;

            for (TimeAllocation tta : task.getTimeAllocations()) {
               total += tta.getAllocated();
            }

            return total;
         }
      };
      tasks.addColumn(totalTimeCol, "Total");
      tasks.setColumnWidth(totalTimeCol, 1, Unit.EM);

      // Remaining
      Cell remainingCell = new ClickableTextCell();
      Column<Task, String> remainingTimeCol = new Column<Task, String>(remainingCell) {

         @Override
         public String getValue(Task task) {
            double total = 0;

            for (TimeAllocation tta : task.getTimeAllocations()) {
               total += tta.getAllocated();
               total -= tta.getCompleted();
            }

            return NumberFormat.getFormat(Constants.ALLOC_FORMAT).format(total);
         }
      };
      remainingTimeCol.setFieldUpdater(new FieldUpdater<Task, String>() {

         @Override
         public void update(int index, Task object, String value) {
            // Show a dialog
            AllocationDialogBox.getPopup(client, object).show();
            changed.add(object);
         }
      });
      tasks.addColumn(remainingTimeCol, "Left");
      tasks.setColumnWidth(remainingTimeCol, 1, Unit.EM);


      // Add a selection model to handle user selection
      final SingleSelectionModel<Task> selectionModel = new SingleSelectionModel<Task>();
      tasks.setSelectionModel(selectionModel);
      selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

         @Override
         public void onSelectionChange(SelectionChangeEvent event) {
            Task selected = selectionModel.getSelectedObject();
            if (selected != null) {
               GWT.log("You selected: " + selected.getId());
            }
         }
      });

      // Just show all tasks
      tasks.setPageSize(Integer.MAX_VALUE - 1);

      // Feed the data
      provider.setList(client.getTasks());

      // Set the total row count. This isn't strictly necessary, but it affects
      // paging calculations, so its good habit to keep the row count up to date.
      tasks.setRowCount(provider.getList().size(), true);

      // Push the data into the widget.
      tasks.setRowData(0, provider.getList());

      // Re-enable the gui
      filter.setEnabled(true);
      addTask.setEnabled(true);
      saveTasks.setEnabled(true);
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
      task.setName("New unsaved task");
      changed.add(task);
      provider.getList().add(task);
      provider.refresh();
   }

   @UiHandler("saveTasks")
   public void saveSomeTasks(ClickEvent event) {
      presenter.doSaveTasks(changed);

      /*GWT.log("Saving " + changed.size() + " tasks: " + changed + ".");
      
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
      });*/
   }

   @Override
   public void redrawTasksTable() {
      tasks.redraw();
   }
}
