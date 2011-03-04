package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceProjectAllocation;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.ejb.entity.TaskTimeAllocation;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import java.util.List;

public class ResourcesViewImpl extends ResizeComposite implements ResourcesView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, ResourcesViewImpl> {
   }
   ClientState client;
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
      // Client
      client = Application.getInjector().getClientState();

      // Not automatically bound items
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
      TextColumn<Resource> groupCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return resource.getGroup() == null ? "" : resource.getGroup().getName();
         }
      };
      resources.addColumn(groupCol, "Group");
      resources.setColumnWidth(groupCol, 3, Unit.EM);

      // Load
      TextColumn<Resource> loadCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return resource.getLoad() + " h";
         }
      };
      resources.addColumn(loadCol, "Load");
      resources.setColumnWidth(loadCol, 1, Unit.EM);

      // Allocation
      TextColumn<Resource> allocCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            for (ResourceProjectAllocation rpa : client.getResourceAllocations()) {
               if (rpa.getResource().getId() == resource.getId()) {
                  return rpa.getPercent() + "%";
               }
            }

            return "";
         }
      };
      resources.addColumn(allocCol, "Allocation");
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

      initWidget(binder.createAndBindUi(this));
   }

   // UI routines
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());


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

      Application.getInjector().getMyResourceService().findAllResourcesForProject(client.getProject(), new AsyncCallback<List<ResourceProjectAllocation>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<ResourceProjectAllocation> result) {
            client.setResourceAllocations(result);
            resources.redraw();
         }
      });
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
