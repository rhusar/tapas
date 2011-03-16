package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.entity.ResourceProjectAllocation;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.client.app.StringConstants;
import java.util.ArrayList;
import java.util.Date;
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
   @UiField
   Anchor allocateResource;

   @Inject
   public ResourcesViewImpl(final ClientState client) {
      // Client
      this.client = client;

      // Not automatically bound items
      provider = new ListDataProvider<Resource>();
      resources = new CellTable<Resource>(provider);

      // Setup CellTable
      resources.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

      // ID
      TextColumn<Resource> idCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return resource.getId() == 0 ? StringConstants.UNSAVEDID : "" + resource.getId();
         }
      };
      resources.addColumn(idCol, "ID");
      resources.setColumnWidth(idCol, 2, Unit.EM);

      // Name
      Cell nameCell = new EditTextCell();
      Column<Resource, String> nameCol = new Column<Resource, String>(nameCell) {

         @Override
         public String getValue(Resource resource) {
            return resource.getName();
         }
      };
      nameCol.setFieldUpdater(new FieldUpdater<Resource, String>() {

         @Override
         public void update(int index, Resource object, String value) {
            object.setName(value);
         }
      });
      resources.addColumn(nameCol, "Name");

      // Group
      List<String> groupOptions = new ArrayList<String>();
      for (ResourceGroup group : client.getGroups()) {
         groupOptions.add(group.getName());
      }
      SelectionCell groupCell = new SelectionCell(groupOptions);
      Column<Resource, String> groupCol = new Column<Resource, String>(groupCell) {

         @Override
         public String getValue(Resource resource) {
            return "" + resource.getGroup();
         }
      };
      groupCol.setFieldUpdater(new FieldUpdater<Resource, String>() {

         @Override
         public void update(int index, Resource object, String value) {

            for (ResourceGroup group : client.getGroups()) {
               if (group.getName().equals(value)) {
                  object.setGroup(group);
               }
            }
         }
      });

//      TextColumn<Resource> groupCol = new TextColumn<Resource>() {
//         @Override
//         public String getValue(Resource resource) {
//            return resource.getGroup() == null ? "" : resource.getGroup().getName();
//         }
//      };

      resources.addColumn(groupCol, "Resource Group");
      resources.setColumnWidth(groupCol, 10, Unit.EM);

      // Load
      Cell loadCell = new EditTextCell();
      Column<Resource, String> loadCol = new Column<Resource, String>(loadCell) {

         @Override
         public String getValue(Resource resource) {
            return resource.getLoad() + "%";
         }
      };
      loadCol.setFieldUpdater(new FieldUpdater<Resource, String>() {

         @Override
         public void update(int index, Resource object, String value) {
            object.setLoad(Byte.parseByte(value.replace('%', ' ')));
            resources.redraw();
         }
      });
      resources.addColumn(loadCol, "Load");
      resources.setColumnWidth(loadCol, 1, Unit.EM);

      // Allocation
      Cell allocCell = new EditTextCell();
      Column<Resource, String> allocCol = new Column<Resource, String>(allocCell) {

         @Override
         public String getValue(Resource resource) {
            for (ResourceProjectAllocation rpa : client.getResourceAllocations()) {
               if (rpa.getResource().equals(resource)) {
                  return rpa.getPercent() + "%";
               }
            }

            return "";
         }
      };
      allocCol.setFieldUpdater(new FieldUpdater<Resource, String>() {

         @Override
         public void update(int index, Resource object, String value) {
            for (ResourceProjectAllocation rpa : client.getResourceAllocations()) {
               if (rpa.getResource().equals(object)) {
                  // Just update this allocation
                  rpa.setPercent(Byte.parseByte(value.replace('%', ' ').trim()));
                  GWT.log("Allocation updated: " + rpa);
                  return;
               }
            }

            // None existing, create new one
            ResourceProjectAllocation newrpa = new ResourceProjectAllocation();
            newrpa.setProject(client.getProject());
            newrpa.setResource(object);
            newrpa.setPercent(Byte.parseByte(value.replace('%', ' ').trim()));
            client.getResourceAllocations().add(newrpa);
            GWT.log("New allocation created: " + newrpa);
         }
      });
      resources.addColumn(allocCol, "Allocation");
      resources.setColumnWidth(allocCol, 10, Unit.EM);

      // Remaining days
      TextColumn<Resource> daysCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            byte alloc = 0;

            for (ResourceProjectAllocation rpa : client.getResourceAllocations()) {
               if (rpa.getResource().equals(resource)) {
                  alloc = rpa.getPercent();
               }
            }

            if (alloc == 0) {
               return "";
            }

//            DecimalFormat df = new DecimalFormat("#.##");
            double res = ((double) (client.getProject().getTargetDate().getTime() - (new Date()).getTime()) / 86400000) * alloc * resource.getLoad() / 100 / 100;
            return "" + NumberFormat.getDecimalFormat().format(res);
         }
      };
      resources.addColumn(daysCol, "Remaining days");
      resources.setColumnWidth(daysCol, 5, Unit.EM);

      // Add a selection model to handle user selection.
      final SingleSelectionModel<Resource> selectionModel = new SingleSelectionModel<Resource>();
      resources.setSelectionModel(selectionModel);
      resources.setPageSize(Integer.MAX_VALUE - 1);
      selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

         @Override
         public void onSelectionChange(SelectionChangeEvent event) {
            Resource selected = selectionModel.getSelectedObject();
            if (selected != null) {
               GWT.log("Selected resource: " + selected.getName());
            }
         }
      });

      initWidget(binder.createAndBindUi(this));
   }

   // UI routines
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());

      if (client.getResources() == null || client.getResourceAllocations() == null) {
         Application.getInjector().getMyResourceService().findAllResources(new AsyncCallback<List<Resource>>() {

            @Override
            public void onFailure(Throwable caught) {
               throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSuccess(List<Resource> result) {
               client.setResources(result);
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
      } else {
         provider.setList(client.getResources());
         resources.setRowCount(client.getResources().size(), true);
         resources.setRowData(0, client.getResources());
         resources.redraw();
      }
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

   @UiHandler("addResource")
   public void addResource(ClickEvent click) {
      Resource newguy = new Resource();
      newguy.setName("New resource");
      provider.getList().add(newguy);
      resources.setRowData(provider.getList());
      resources.setRowCount(provider.getList().size());
      resources.redraw();
      presenter.setUnsaved(true);
   }

   @UiHandler("allocateResource")
   public void allocateResource(ClickEvent click) {
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
