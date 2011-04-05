package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocation;
import com.radoslavhusar.tapas.ejb.stats.ResourcePriorityAllocationStats;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocationPK;
import com.radoslavhusar.tapas.ejb.entity.Trait;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.ClientState;
import com.radoslavhusar.tapas.war.client.app.Constants;
import com.radoslavhusar.tapas.war.client.ui.DynamicSelectionCell;
import com.radoslavhusar.tapas.war.client.ui.SizeableEditTextCell;
import com.radoslavhusar.tapas.war.client.util.DataUtil;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResourcesViewImpl extends ResizeComposite implements ResourcesView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);
   public static final int NUMBER_COL_EM = 8;
   private final TaskResourceServiceAsync service;

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
   private DynamicSelectionCell groupCell;
   static Renderer<Resource> renderer = new AbstractRenderer<Resource>() {

      @Override
      public String render(Resource object) {
         return (object == null ? "" : object.getName());
      }
   };

   @Inject
   public ResourcesViewImpl(final ClientState client, TaskResourceServiceAsync service) {
      this.service = service;
      this.client = client;

      /*simplePopup.setWidth("200px");
      simplePopup.setWidget(new HTML(Constants.RESOURCE_ALLOCATION));*/

      // Not automatically bound items
      provider = new ListDataProvider<Resource>();
      resources = new CellTable<Resource>(provider);

      // Setup CellTable
      resources.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

      // ID
      TextColumn<Resource> idCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return resource.getId() == null ? Constants.UNSAVED_ID : resource.getId().toString();
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
      resources.addColumn(nameCol, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            return "Resource Name";
         }
      }, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            return "" + provider.getList().size() + " resources total";
         }
      });

      // Traits
      Cell traitCell = new ClickableTextCell();
      Column<Resource, String> traitCol = new Column<Resource, String>(traitCell) {

         @Override
         public String getValue(Resource resource) {
            return resource.getTraits() == null ? "null" : (resource.getTraits().size() + "x");
         }
      };
      traitCol.setFieldUpdater(new FieldUpdater<Resource, String>() {

         @Override
         public void update(int index, Resource resource, String value) {
            ResourceTraitsDialogBox.getPopup(resource).show();
            changed.add(resource);
         }
      });
      nameCol.setSortable(true);
      resources.addColumn(traitCol, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            return "Traits";
         }
      }, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            return "";
         }
      });


      // Group
      List<String> groupOptions = new ArrayList<String>();
      groupOptions.add("");
      /*
      for (ResourceGroup group : client.getGroups()) {
      groupOptions.add(group.getName());
      }
      SelectionCell groupCell = new SelectionCell(groupOptions);
       */
      groupCell = new DynamicSelectionCell(groupOptions);
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
      // TODO: this is actually "Resource Group" but lets make it more human and call it Team as for human resources.
      resources.addColumn(groupCol, "Team");
      resources.setColumnWidth(groupCol, 10, Unit.EM);


      // Contract
      Cell contractCell = new SizeableEditTextCell(2); //new EditTextCell();
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
      resources.addColumn(contractCol, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            return "Contract";
         }
      }, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            int sum = 0;
            for (Resource r : provider.getList()) {
               sum += r.getContract();
            }
            return "" + sum;
         }
      });
      resources.setColumnWidth(contractCol, 1, Unit.EM);


      // Project Allocation
      Cell allocCell = new SizeableEditTextCell(2); //new EditTextCell();
      Column<Resource, String> allocCol = new Column<Resource, String>(allocCell) {

         @Override
         public String getValue(Resource resource) {
            return "" + resource.getResourceAllocations().get(0).getPercent();
         }
      };
      allocCol.setFieldUpdater(new FieldUpdater<Resource, String>() {

         @Override
         public void update(int index, Resource object, String value) {
            changed.add(object);

            // is it in the cache?
            ResourceAllocation rpa = object.getResourceAllocations().get(0); //client.getResourceAllocations().get(object.getId());
            if (rpa != null) {
               if (rpa.getKey().getResource().equals(object)) {
                  // Just update this allocation
                  rpa.setPercent(Byte.parseByte(value.replace('%', ' ').trim()));
                  List newa = new ArrayList();
                  newa.add(rpa);
                  object.setResourceAllocations(newa);
                  GWT.log("Allocation updated: " + rpa);

                  resources.redraw();
                  return;
               }
            }
            //}

            // None existing, create new one
            ResourceAllocation newrpa;

            if (object.getResourceAllocations() != null && !object.getResourceAllocations().isEmpty()) {
               newrpa = object.getResourceAllocations().get(0);
            } else {
               newrpa = new ResourceAllocation();
               newrpa.setKey(new ResourceAllocationPK(client.getProject(), object));
            }

            newrpa.setPercent(Byte.parseByte(value.replace('%', ' ').trim()));
            List newa = new ArrayList();
            newa.add(newrpa);
            object.setResourceAllocations(newa);
            //client.getResourceAllocations().add(newrpa);
            GWT.log("New allocation created: " + newrpa);
            resources.redraw();
         }
      });
      resources.addColumn(allocCol, "Project Allocation");
      resources.setColumnWidth(allocCol, 10, Unit.EM);

      // Updated to http://google-web-toolkit.googlecode.com/svn/javadoc/2.2/com/google/gwt/cell/client/NumberCell.html but if we want to display ratio..

      // Number cells
      NumberCell daysNumberCell = new NumberCell(NumberFormat.getFormat("0.00"));
      NumberCell percentageNumberCell = new NumberCell(NumberFormat.getPercentFormat());


      // Remaining days
      TextColumn<Resource> daysCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            /* byte alloc = resource.getResourceAllocations().get(0).getPercent();
            double res = ((double) (client.getProject().getTargetDate().getTime() - (new Date()).getTime()) / 86400000) * alloc * resource.getContract() / 100 / 100;
            return "" + NumberFormat.getDecimalFormat().format(res);*/
            return NumberFormat.getFormat(Constants.ALLOC_FORMAT).format(
                    DataUtil.remainingUntil(client.getProject().getTargetDate(), resource));
         }
      };
      resources.addColumn(daysCol, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            return "Days remaining";
         }
      }, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            double dsum = 0;
            for (Resource resource : provider.getList()) {
               dsum += DataUtil.remainingUntil(client.getProject().getTargetDate(), resource);
            }
            return NumberFormat.getFormat(Constants.ALLOC_FORMAT).format(dsum);
         }
      });
      resources.setColumnWidth(daysCol, 5, Unit.EM);

      // Clean Remaining days
      TextColumn<Resource> cleanDaysCol = new TextColumn<Resource>() {

         @Override
         public String getValue(Resource resource) {
            return NumberFormat.getFormat(Constants.ALLOC_FORMAT).format(
                    DataUtil.cleanTaxDays(DataUtil.remainingUntil(client.getProject().getTargetDate(), resource), client.getProject().getTax()));
         }
      };
      resources.addColumn(cleanDaysCol, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            return "Days less Tax";
         }
      }, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            double dsum = 0;
            for (Resource resource : provider.getList()) {
               dsum += DataUtil.cleanTaxDays(DataUtil.remainingUntil(client.getProject().getTargetDate(), resource), client.getProject().getTax());
            }
            return NumberFormat.getFormat(Constants.ALLOC_FORMAT).format(dsum);
         }
      });
      resources.setColumnWidth(cleanDaysCol, NUMBER_COL_EM, Unit.EM);

      // Assigned P1
      Column<Resource, Number> assignedP1Col = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
            if (rad == null) {
               return null;
            } else {
               return rad.getP1Allocation();
            }
         }
      };
      resources.addColumn(assignedP1Col, "P1 Assigned");
      resources.setColumnWidth(assignedP1Col, NUMBER_COL_EM, Unit.EM);

      // Remaining P1
      Column<Resource, Number> remainingP1Col = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
            if (rad == null) {
               return null;
            } else {
               return rad.getP1Allocation() - rad.getP1Completed();
            }
         }
      };
      resources.addColumn(remainingP1Col, "P1 Remaining");
      resources.setColumnWidth(remainingP1Col, NUMBER_COL_EM, Unit.EM);

      // Assigned P2
      Column<Resource, Number> assignedP2Col = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
            if (rad == null) {
               return null;
            } else {
               return rad.getP2Allocation();
            }
         }
      };
      resources.addColumn(assignedP2Col, "P2 Assigned");
      resources.setColumnWidth(assignedP2Col, NUMBER_COL_EM, Unit.EM);

      // Assigned P3
      Column<Resource, Number> assignedP3Col = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
            if (rad == null) {
               return null;
            } else {
               return rad.getP3Allocation();
            }
         }
      };
      resources.addColumn(assignedP3Col, "P3 Assigned");
      resources.setColumnWidth(assignedP3Col, NUMBER_COL_EM, Unit.EM);

      // Assigned total
      Column<Resource, Number> assignedTotal = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
            if (rad == null) {
               return null;
            } else {
               // Sum of P1,P2,P3,TBD
               return DataUtil.calculateAssigned(rad);
            }
         }
      };
      resources.addColumn(assignedTotal, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            return "Total Assigned";
         }
      }, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            int sum = 0;
            for (Resource resource : provider.getList()) {
               ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
               if (rad == null) {
                  continue;
               }
               sum += DataUtil.calculateAssigned(rad);
            }
            return "" + sum;
         }
      });
      resources.setColumnWidth(assignedTotal, NUMBER_COL_EM, Unit.EM);

      // Remaining total
      Column<Resource, Number> remainingTotal = new Column<Resource, Number>(daysNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
            if (rad == null) {
               return null;
            } else {
               // Sum of P1,P2,P3,TBD
               return DataUtil.calculateRemaining(rad);
            }
         }
      };
      resources.addColumn(remainingTotal, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            return "Total Remaining";
         }
      }, new Header<String>(new TextCell()) {

         @Override
         public String getValue() {
            int sum = 0;
            for (Resource resource : provider.getList()) {
               ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
               if (rad == null) {
                  continue;
               }
               sum += DataUtil.calculateRemaining(rad);
            }
            return "" + sum;
         }
      });
      resources.setColumnWidth(remainingTotal, NUMBER_COL_EM, Unit.EM);

      // Load %
      Column<Resource, Number> loadCol = new Column<Resource, Number>(percentageNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            // Fraction of ALL assigned - sum of done  
            ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
            if (rad == null) {
               return null;
            }
            ResourceAllocation ra = resource.getResourceAllocations().get(0);
            if (ra == null) {
               return null;
            }

            return DataUtil.calculateRemaining(rad) / DataUtil.remainingUntil(client.getProject().getTargetDate(), resource);
         }
      };
      resources.addColumn(loadCol, "Total Load");
      resources.setColumnWidth(loadCol, NUMBER_COL_EM, Unit.EM);

      // Load P1 %
      Column<Resource, Number> loadP1Col = new Column<Resource, Number>(percentageNumberCell) {

         @Override
         public Number getValue(Resource resource) {
            // Fraction of assigned P1 - sum of done  
            ResourcePriorityAllocationStats rad = client.getResourceData().get(resource.getId());
            if (rad == null) {
               return null;
            }
            ResourceAllocation ra = resource.getResourceAllocations().get(0);
            if (ra == null) {
               return null;
            }

            return rad.getP1Allocation() / DataUtil.remainingUntil(client.getProject().getTargetDate(), resource);
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
      GWT.log("ResourcesViewImpl is constructed!");
   }

   // UI routines
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());

      changed = new HashSet<Resource>();

      if (client.getProjectId() == null) {
         // no project chosen
         resources.setRowCount(0, true);
         return;
      }

      // Logic; if client cache is full, dont reload
      if (client.getResources() != null && client.getResourceData() != null && client.getGroups() != null) {
         renderResources();
         return;
      }

      // Otherwise - reload
      toSync = 3; // number of async services which MUST sync before render
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
      service.fetchAllResourceDataForProject(client.getProjectId(), new AsyncCallback<Map<Long, ResourcePriorityAllocationStats>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(Map<Long, ResourcePriorityAllocationStats> result) {
            client.setResourceData(result);
            toSyncRender();
         }
      });
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
   }
   private int toSync;

   private void toSyncRender() {
      toSync--;

      if (toSync == 0) {
         renderResources();
      }
   }

   private void renderResources() {
      for (ResourceGroup group : client.getGroups()) {
         groupCell.addOption(group.getName());
      }
      provider.setList(client.getResources());
      resources.setRowCount(provider.getList().size(), true);
      resources.setRowData(0, provider.getList());
      resources.redraw();
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

   @UiHandler("addResource")
   public void addResource(ClickEvent click) {
      byte b = 100;

      Resource newResource = new Resource();
      changed.add(newResource);
      newResource.setName("New resource");
      newResource.setContract(b);

      // Allocation
      ResourceAllocation fullAllocation = new ResourceAllocation();
      fullAllocation.setPercent(b);
      fullAllocation.setKey(new ResourceAllocationPK(client.getProject(), newResource));
      List allodcs = new ArrayList();
      allodcs.add(fullAllocation);
      newResource.setResourceAllocations(allodcs);

      // Traits (none)
      Set<Trait> noneTraits = new HashSet<Trait>();
      newResource.setTraits(noneTraits);

      provider.getList().add(newResource);
      resources.setRowData(provider.getList());
      resources.setRowCount(provider.getList().size());
      resources.redraw();
      presenter.setUnsaved(true);
   }

   @UiHandler("allocateResource")
   public void allocateResource(ClickEvent event) {
      // Reposition the popup relative to the link
      Widget source = (Widget) event.getSource();
      int left = source.getAbsoluteLeft() - 20;
      int top = source.getAbsoluteTop() + 10;
      simplePopup.setPopupPosition(left, top);
      final Button addButton = new Button("Allocate");
      addButton.setEnabled(true);
      HorizontalPanel horizontal = new HorizontalPanel();
      final ValueListBox<Resource> resourceSwitch = new ValueListBox<Resource>(renderer);
      ArrayList tmp = new ArrayList(1);
      final Resource r = new Resource();
      r.setName("");
      tmp.add(r);
      //resourceSwitch.setAcceptableValues(tmp);
      resourceSwitch.setValue(r);

      service.findAllResourcesNotOnProject(client.getProjectId(), new AsyncCallback<List<Resource>>() {

         @Override
         public void onFailure(Throwable caught) {
            addButton.setEnabled(false);
         }

         @Override
         public void onSuccess(List<Resource> result) {
            if (!result.isEmpty()) {
               resourceSwitch.setAcceptableValues(result);
               resourceSwitch.setValue(result.get(0));
               addButton.setEnabled(true);
            } else {
               addButton.setEnabled(false);
            }
         }
      });
      horizontal.add(resourceSwitch);

      addButton.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            Resource res = resourceSwitch.getValue();
            client.getResources().add(res);

            ResourceAllocation fullAllocation = new ResourceAllocation();
            fullAllocation.setPercent((byte) 100);
            fullAllocation.setKey(new ResourceAllocationPK(client.getProject(), res));
            List allodcs = new ArrayList();
            allodcs.add(fullAllocation);
            res.setResourceAllocations(allodcs);

            changed.add(res);
            simplePopup.hide();

            // Stupid hack!
            //Application.getInjector().getPlaceController().goTo(new ResourcesPlace(client.getProjectId()));
            //resources.setRowCount(provider.getList().size() + 1);
            //resources.redraw();

            renderResources();
         }
      });
      horizontal.add(addButton);
      simplePopup.setWidget(horizontal);

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
