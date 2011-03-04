package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.TaskTimeAllocation;
import java.util.List;

public class ResourcesViewImpl extends ResizeComposite implements ResourcesView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, ResourcesViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField(provided = true)
   CellTable<Resource> resources = new CellTable<Resource>();
   ListDataProvider<Resource> provider;
   SimplePager pager;
   @UiField
   Anchor addResource;

   public ResourcesViewImpl() {
      // Not automatically bound
      provider = new ListDataProvider<Resource>();
      resources = new CellTable<Resource>(provider);

      // Setup CellTable
      resources.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

      // ID
      TextColumn<Resource> idCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return "" + resource.getId();
         }
      };
      resources.addColumn(idCol, "ID");
      resources.setColumnWidth(idCol, 2, Unit.EM);

      // Name
      TextColumn<Resource> nameCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return resource.getName();
         }
      };
      resources.addColumn(nameCol, "Name");

      // Load
      TextColumn<Resource> loadCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return resource.getLoad() + "%";
         }
      };
      resources.addColumn(loadCol, "Load");
      resources.setColumnWidth(loadCol, 1, Unit.EM);

      // Allocation
      TextColumn<Resource> allocCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return "N/I";
         }
      };
      resources.addColumn(allocCol, "Status");
      resources.setColumnWidth(allocCol, 10, Unit.EM);

      // Add a selection model to handle user selection.
      final SingleSelectionModel<Resource> selectionModel = new SingleSelectionModel<Resource>();
      resources.setSelectionModel(selectionModel);
      resources.setPageSize(Integer.MAX_VALUE - 1);
      selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

         @Override
         public void onSelectionChange(SelectionChangeEvent event) {
            Resource selected = selectionModel.getSelectedObject();
            if (selected != null) {
               GWT.log("You selected: " + selected.getName());
            }
         }
      });


/*
      // Phases Times
      Project project = Application.getInjector().getClientState().getProject();
      if (project != null) {
         for (final ProjectPhase phase : project.getPhases()) {
            TextColumn<Task> timePhaseCol = new TextColumn<Task>() {

               @Override
               public String getValue(Task task) {
                  for (TaskTimeAllocation tta : task.getTimeAllocations()) {
                     if (tta.getPhase().getId() == phase.getId()) {
                        return "" + tta.getTimeAllocation();
                     }
                  }
                  return "";
               }
            };

            resources.addColumn(timePhaseCol, phase.getName().substring(0, 4));
            resources.setColumnWidth(timePhaseCol, 2, Unit.EM);
         }
      }
*/


      Application.getInjector().getMyResourceService().findAllResources(new AsyncCallback<List<Resource>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Resource> result) {
            provider.setList(result);
            resources.setRowCount(result.size(), true);
            resources.setRowData(0, result);
         }
      });

      initWidget(binder.createAndBindUi(this));
   }

   // UI routines
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());
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
