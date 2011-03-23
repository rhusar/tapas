package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
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
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocation;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocationPK;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.client.app.Constants;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResourcesViewImpl extends ResizeComposite implements ResourcesView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);
   public static final int NUMBER_COL_EM = 8;

   interface Binder extends UiBinder<Widget, ResourcesViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   ClientState client;
   Map<Resource, Double[]> map;
   private Set<Resource> changed;
   @UiField(provided = true)
   CellTable<Resource> resources = new CellTable<Resource>();
   ListDataProvider<Resource> provider;
   SimplePager pager;
   @UiField
   Anchor addResource;
   @UiField
   Anchor allocateResource;
   final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);

   @Inject
   public ResourcesViewImpl(final ClientState client) {
      GWT.log("ResourcesViewImpl " + this + "is constructed!");
      
      // Client
      this.client = client;

      simplePopup.setWidth("200px");
      simplePopup.setWidget(new HTML(Constants.RESOURCE_ALLOCATION));

      // Not automatically bound items
      provider = new ListDataProvider<Resource>();
      resources = new CellTable<Resource>(provider);

      // Setup CellTable
      resources.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

      // ID
      TextColumn<Resource> idCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return resource.getId() == 0 ? Constants.UNSAVEDID : "" + resource.getId();
         }
      };
      /* 
      FIXME: http://code.google.com/webtoolkit/doc/latest/DevGuideUiCellTable.html
      
      idCol.setSortable(true);
      ListHandler<Resource> idColSortHandler = new ListHandler<Resource>(provider.getList());
      idColSortHandler.setComparator(idCol, new Comparator<Resource>() {
      
      @Override
      public int compare(Resource o1, Resource o2) {
      return (int) (o1.getId() - o2.getId());
      }
      });
      resources.addColumnSortHandler(idColSortHandler);
       */
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
            changed.add(object);
         }
      });
      nameCol.setSortable(true);
      resources.addColumn(nameCol, "Resource Name");
      // resources.setColumnWidth(nameCol, 10, Unit.EM);

      // Group
      List<String> groupOptions = new ArrayList<String>();
      groupOptions.add("");
      for (ResourceGroup group : client.getGroups()) {
         groupOptions.add(group.getName());
      }
      SelectionCell groupCell = new SelectionCell(groupOptions);
      Column<Resource, String> groupCol = new Column<Resource, String>(groupCell) {

         @Override
         public String getValue(Resource resource) {
            return "" + (resource.getGroup() != null ? resource.getGroup().getName() : "");
         }
      };
      groupCol.setFieldUpdater(new FieldUpdater<Resource, String>() {

         @Override
         public void update(int index, Resource object, String value) {
            changed.add(object);

            if (value.isEmpty()) {
               object.setGroup(null);
               return;
            }
            for (ResourceGroup group : client.getGroups()) {
               if (group.getName().equals(value)) {
                  object.setGroup(group);
                  return;
               }
            }
         }
      });
      resources.addColumn(groupCol, "Resource Group");
      resources.setColumnWidth(groupCol, 10, Unit.EM);

      // Load
      Cell contractCell = new EditTextCell();
      Column<Resource, String> contractCol = new Column<Resource, String>(contractCell) {

         @Override
         public String getValue(Resource resource) {
            return "" + resource.getContract();// + "%";
         }
      };
      contractCol.setFieldUpdater(new FieldUpdater<Resource, String>() {

         @Override
         public void update(int index, Resource object, String value) {
            changed.add(object);
            object.setContract(Byte.parseByte(value.replace('%', ' ').trim()));
            resources.redraw();
         }
      });
      resources.addColumn(contractCol, "Contract %");
      resources.setColumnWidth(contractCol, 1, Unit.EM);

      // Allocation
      Cell allocCell = new EditTextCell();
      Column<Resource, String> allocCol = new Column<Resource, String>(allocCell) {

         @Override
         public String getValue(Resource resource) {
//            for (ResourceAllocation rpa : client.getResourceAllocations()) {
//               if (rpa.getKey().getResource().getId() == resource.getId()) {
//                  return rpa.getPercent() + "%";
//               }
//            }
//
//            return "";
            return "" + resource.getAllocations().get(0).getPercent();
         }
      };
      allocCol.setFieldUpdater(new FieldUpdater<Resource, String>() {

         @Override
         public void update(int index, Resource object, String value) {
            changed.add(object);

            for (ResourceAllocation rpa : client.getResourceAllocations()) {
               if (rpa.getKey().getResource().equals(object)) {
                  // Just update this allocation
                  rpa.setPercent(Byte.parseByte(value.replace('%', ' ').trim()));
                  List newa = new ArrayList();
                  newa.add(rpa);
                  object.setAllocations(newa);
                  GWT.log("Allocation updated: " + rpa);

                  resources.redraw();
                  return;
               }
            }

            // None existing, create new one
            ResourceAllocation newrpa = new ResourceAllocation();
            newrpa.setKey(new ResourceAllocationPK(client.getProject(), object));
            newrpa.setPercent(Byte.parseByte(value.replace('%', ' ').trim()));
            List newa = new ArrayList();
            newa.add(newrpa);
            object.setAllocations(newa);
            client.getResourceAllocations().add(newrpa);
            GWT.log("New allocation created: " + newrpa);
            resources.redraw();
         }
      });
      resources.addColumn(allocCol, "Project Allocation %");
      resources.setColumnWidth(allocCol, 10, Unit.EM);

      // TODO: Update to http://google-web-toolkit.googlecode.com/svn/javadoc/2.2/com/google/gwt/cell/client/NumberCell.html
      // Number cells
      NumberCell daysNumberCell = new NumberCell(NumberFormat.getFormat("0.00"));
      NumberCell percentageNumberCell = new NumberCell(NumberFormat.getPercentFormat());

      // Remaining days
      TextColumn<Resource> daysCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            byte alloc = resource.getAllocations().get(0).getPercent();
            double res = ((double) (client.getProject().getTargetDate().getTime() - (new Date()).getTime()) / 86400000) * alloc * resource.getContract() / 100 / 100;
            return "" + NumberFormat.getDecimalFormat().format(res);
         }
      };
      resources.addColumn(daysCol, "Remaining days");
      resources.setColumnWidth(daysCol, 5, Unit.EM);

      // Clean Remaining days
      TextColumn<Resource> cleanDaysCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return "N/I";
         }
      };
      resources.addColumn(cleanDaysCol, "Days less PTO/Tax");
      resources.setColumnWidth(cleanDaysCol, NUMBER_COL_EM, Unit.EM);

      // Assigned P1
      Column<Resource, Number> assignedP1Col = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            if (map.get(resource) == null) {
               return null;
            } else {
               return map.get(resource)[0];
            }
         }
      };
      resources.addColumn(assignedP1Col, "P1 Assigned");
      resources.setColumnWidth(assignedP1Col, NUMBER_COL_EM, Unit.EM);


      // Remaining P1
      Column<Resource, Number> remainingP1Col = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            if (map.get(resource) == null) {
               return null;
            } else {
               return map.get(resource)[0] - map.get(resource)[1];
            }
         }
      };
      resources.addColumn(remainingP1Col, "P1 Remaining");
      resources.setColumnWidth(remainingP1Col, NUMBER_COL_EM, Unit.EM);

      // Assigned P2
      Column<Resource, Number> assignedP2Col = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            if (map.get(resource) == null) {
               return null;
            } else {
               return map.get(resource)[2];
            }
         }
      };
      resources.addColumn(assignedP2Col, "P2 Assigned");
      resources.setColumnWidth(assignedP2Col, NUMBER_COL_EM, Unit.EM);

      // Assigned P3
      Column<Resource, Number> assignedP3Col = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            if (map.get(resource) == null) {
               return null;
            } else {
               return map.get(resource)[3] - map.get(resource)[2];
            }
         }
      };
      resources.addColumn(assignedP3Col, "P3 Assigned");
      resources.setColumnWidth(assignedP3Col, NUMBER_COL_EM, Unit.EM);

      // Assigned total
      Column<Resource, Number> assignedTotal = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            if (map.get(resource) == null) {
               return null;
            } else {
               // Sum of P1,P2,P3,PX
               return map.get(resource)[0] + map.get(resource)[2] + map.get(resource)[4] + map.get(resource)[6];
            }
         }
      };
      resources.addColumn(assignedTotal, "Total Assigned");
      resources.setColumnWidth(assignedTotal, NUMBER_COL_EM, Unit.EM);

      // Remaining total
      Column<Resource, Number> remainingTotal = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            if (map.get(resource) == null) {
               return null;
            } else {
               // Difference of assigned - sum of done [P1,P2,P3,PX]
               return map.get(resource)[0] + map.get(resource)[2] + map.get(resource)[4] + map.get(resource)[6]
                       - (map.get(resource)[1] + map.get(resource)[3] + map.get(resource)[5] + map.get(resource)[7]);
            }
         }
      };
      resources.addColumn(remainingTotal, "Total Remaining");
      resources.setColumnWidth(remainingTotal, NUMBER_COL_EM, Unit.EM);

      // Load %
      Column<Resource, Number> loadCol = new Column<Resource, Number>(percentageNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            if (map.get(resource) == null) {
               return null;
            } else {
               // Fraction of assigned - sum of done [P1,P2,P3,PX]
               // DOTO: Make this cleaner
//               byte alloc = 0;
//
//               for (ResourceAllocation rpa : client.getResourceAllocations()) {
//                  if (rpa.getKey().getResource().equals(resource)) {
//                     alloc = rpa.getPercent();
//                  }
//               }
//
//               if (alloc == 0) {
//                  return null;
//               }

               // FIXME: code repetition
               //double res = ((double) (client.getProject().getTargetDate().getTime() - (new Date()).getTime()) / 86400000) * alloc * resource.getContract() / 100 / 100;
               byte alloc = resource.getAllocations().get(0).getPercent();
               double res = ((double) (client.getProject().getTargetDate().getTime() - (new Date()).getTime()) / 86400000) * alloc * resource.getContract() / 100 / 100;


               return (map.get(resource)[0] + map.get(resource)[2] + map.get(resource)[4] + map.get(resource)[6]
                       - (map.get(resource)[1] + map.get(resource)[3] + map.get(resource)[5] + map.get(resource)[7])) / (res);
            }
         }
      };
      resources.addColumn(loadCol, "Total Load %");
      resources.setColumnWidth(loadCol, NUMBER_COL_EM, Unit.EM);

      // Load P1 %
      Column<Resource, Number> loadP1Col = new Column<Resource, Number>(percentageNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            // FIXME: code repetition
            byte alloc = resource.getAllocations().get(0).getPercent();
            double res = ((double) (client.getProject().getTargetDate().getTime() - (new Date()).getTime()) / 86400000) * alloc * resource.getContract() / 100 / 100;

            if (map.get(resource) == null) {
               return null;
            } else {
               return (map.get(resource)[0] - map.get(resource)[1]) / res;
            }
         }
      };
      resources.addColumn(loadP1Col, "P1 Load %");
      resources.setColumnWidth(loadP1Col, NUMBER_COL_EM, Unit.EM);



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

      changed = new HashSet<Resource>();

      // Logic; if client cache is full, dont reload

      if (client.getResources() != null && client.getResourceAllocations() != null) {
         renderResources();
         return;
      }

      // Otherwise - reload

      toSync = 2;

      Application.getInjector().getService().findAllAllocationsForProject(client.getProject().getId(), new AsyncCallback<List<ResourceAllocation>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<ResourceAllocation> result) {
            client.setResourceAllocations(result);
            toSyncRender();
         }
      });

      Application.getInjector().getService().findAllResourceStatsForProject(client.getProject().getId(), new AsyncCallback<Map<Resource, Double[]>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(Map<Resource, Double[]> result) {
            client.setResources(result);
            toSyncRender();
         }
      });
   }
   private int toSync;

   private void toSyncRender() {
      toSync--;

      if (toSync == 0) {
         renderResources();
      }
   }

   private void renderResources() {
      map = client.getResources();
      List<Resource> asList = new ArrayList(client.getResources().keySet());
      provider.setList(asList);
      resources.setRowCount(asList.size(), true);
      resources.setRowData(0, asList);
      resources.redraw();
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

   @UiHandler("addResource")
   public void addResource(ClickEvent click) {
      Resource newguy = new Resource();
      changed.add(newguy);
      newguy.setName("New resource");
      ResourceAllocation fullAllocation = new ResourceAllocation();
      fullAllocation.setPercent(new Byte("100"));
      fullAllocation.setKey(new ResourceAllocationPK(client.getProject(), newguy));
      List allodcs = new ArrayList();
      allodcs.add(fullAllocation);
      newguy.setAllocations(allodcs);
      provider.getList().add(newguy);
      resources.setRowData(provider.getList());
      resources.setRowCount(provider.getList().size());
      resources.redraw();
      presenter.setUnsaved(true);
   }

   @UiHandler("allocateResource")
   public void allocateResource(ClickEvent event) {
      // Reposition the popup relative to the link
      Widget source = (Widget) event.getSource();
      int left = source.getAbsoluteLeft() + 10;
      int top = source.getAbsoluteTop() + 10;
      simplePopup.setPopupPosition(left, top);

      // Show the popup
      simplePopup.show();
   }

   @UiHandler("saveResources")
   public void saveResources(ClickEvent click) {
      GWT.log("Saving resources: " + changed);
      Application.getInjector().getService().editResourcesForProject(client.getProject().getId(), changed, new AsyncCallback<Void>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(Void result) {
            changed.clear();
         }
      });
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
};
