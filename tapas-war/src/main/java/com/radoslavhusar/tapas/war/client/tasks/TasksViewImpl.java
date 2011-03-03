package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.ejb.entity.Task;
import java.util.ArrayList;
import java.util.List;

public class TasksViewImpl extends ResizeComposite implements TasksView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, TasksViewImpl> {
   }
   @UiField(provided = true)
   CellTable table = new CellTable<Task>();
//   @UiField
//   Grid grid;
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField
   TextBox filter;
   ListDataProvider<Task> provider;
   SimplePager pager;
   /*public class MyHeader extends Header {

   private String text;

   public MyHeader(String text) {
   super(new ClickableTextCell());
   this.text = text;
   }

   @Override
   public String getValue() {
   return text;
   }

   @Override
   public void onBrowserEvent(Element elem, NativeEvent event) {
   super.onBrowserEvent(elem, event);
   GWT.log("" + event.getType().equals("click"));

   //         PopupPanel pp = new PopupPanel(true);
   //         FormPanel fp = new FormPanel();
   //         fp.add(new TextField());
   //         pp.setWidget();
   //         pp.show();
   }
   }*/

   public TasksViewImpl() {
      provider = new ListDataProvider<Task>() {

         @Override
         /**
          * Work around...
          */
         public void refresh() {
            super.refresh();
            this.onRangeChanged(table);
         }

         @Override
         protected void onRangeChanged(HasData<Task> display) {
            if (filter.getText().isEmpty()) {
               display.setVisibleRange(0, this.getList().size());
               display.setRowData(0, this.getList());
            } else {
               ArrayList<Task> filteredlist = new ArrayList<Task>();

               for (Task t : this.getList()) {
                  if (t.getSummary().contains(filter.getText())) {
                     filteredlist.add(t);
                  }
               }

               display.setRowCount(filteredlist.size());
               display.setRowData(0, filteredlist);
               GWT.log("Filtered by " + filter.getText());
            }
         }
      };
      table = new CellTable<Task>(provider);

      initWidget(binder.createAndBindUi(this));

      filter.addKeyUpHandler(new KeyUpHandler() {

         @Override
         public void onKeyUp(KeyUpEvent event) {
            if (event.getNativeKeyCode() == 27) {
               // if ESC is pressed, clear the filter
               filter.setText("");
            }

            // Do the filter!!
            provider.refresh();
         }
      });



//      ProvidesKey<Task> keyProvider = new ProvidesKey<Task>() {
//
//         @Override
//         public Object getKey(Task item) {
//            // Always do a null check.
//            return (item == null) ? null : item.getId();
//         }
//      };




//      table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

      TextColumn<Task> idColumn = new TextColumn<Task>() {

         @Override
         public String getValue(Task task) {
            return "" + task.getId();
         }
      };

      table.addColumn(idColumn, "ID");
      table.addColumnStyleName(1, ".idColumnStyle");
      table.setColumnWidth(idColumn, 2, Unit.EM);

      TextColumn<Task> nameColumn = new TextColumn<Task>() {

         @Override
         public String getValue(Task task) {
            return task.getSummary();
         }
      };
      table.addColumn(nameColumn, "Summary");



      // Add a selection model to handle user selection.
      final SingleSelectionModel<Task> selectionModel = new SingleSelectionModel<Task>();
      table.setSelectionModel(selectionModel);
      selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

         @Override
         public void onSelectionChange(SelectionChangeEvent event) {
            Task selected = selectionModel.getSelectedObject();
            if (selected != null) {
               // Window.alert("You selected: " + selected.getTaskId());
               presenter.goToEdit("" + selected.getId());
            }
         }
      });

      table.setPageSize(Integer.MAX_VALUE - 1);


      Application.getInjector().getMyResourceService().findAll(new AsyncCallback<List<Task>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Task> result) {
//            List<Task> s = new ArrayList(result);
            provider.setList(result);

            // Set the total row count. This isn't strictly necessary, but it affects
            // paging calculations, so its good habit to keep the row count up to date.
            table.setRowCount(result.size(), true);

            // Push the data into the widget.
            table.setRowData(0, result);
         }
      });

//   @UiHandler("table")
//   void onxclick(ClickEvent ce) {
//      Cell c = table.getCellForEvent(ce);
//      String id = table.getText(c.getRowIndex(), 0);
//      presenter.goToEdit(id);
//   }

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
