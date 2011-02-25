/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.radoslavhusar.tapas.ejb.entity.Employee;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.ejb.entity.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
public class TaskListViewImpl extends ResizeComposite implements TaskListView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, TaskListViewImpl> {
   }
//   @UiField
//   FlexTable header;
   @UiField(provided = true)
   CellTable table = new CellTable<Task>();
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;

   public TaskListViewImpl() {
      initWidget(binder.createAndBindUi(this));

      table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);


      TextColumn<Task> idColumn = new TextColumn<Task>() {

         @Override
         public String getValue(Task task) {
            return "" + task.getId();
         }
      };
      table.addColumn(idColumn, "ID");
      TextColumn<Task> nameColumn = new TextColumn<Task>() {

         @Override
         public String getValue(Task task) {
            return task.getSummary();
         }
      };
      table.addColumn(nameColumn, "Summary");


      //    DateCell dateCell = new DateCell();
//    Column<Contact, Date> dateColumn = new Column<Contact, Date>(dateCell) {
//      @Override
//      public Date getValue(Contact object) {
//        return object.birthday;
//      }
//    };
//    table.addColumn(dateColumn, "Birthday");

//      header.setText(0, 0, "Task ID");
//      header.setText(0, 1, "Text");
//      header.setText(0, 2, "Some Integer");
//
//      int i = 0;
//      for (Task t : TaskListDummySource.fetch()) {
//          table.setText(i, 0, "" + t.getTaskId());
//         table.setText(i, 1, t.getSummary());
//         table.setText(i, 2, String.valueOf(t.getSomeinteger()));
//         i++;
//      }

      // Add a selection model to handle user selection.
      final SingleSelectionModel<Task> selectionModel = new SingleSelectionModel<Task>();
      table.setSelectionModel(selectionModel);
      selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

         @Override
         public void onSelectionChange(SelectionChangeEvent event) {
            Task selected = selectionModel.getSelectedObject();
            if (selected != null) {
//               Window.alert("You selected: " + selected.getTaskId());
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
            List<Task> s = new ArrayList(result); // = TaskListDummySource.fetch();

            // Set the total row count. This isn't strictly necessary, but it affects
            // paging calculations, so its good habit to keep the row count up to date.
            table.setRowCount(s.size(), true);

            // Push the data into the widget.
            table.setRowData(0, s);
         }
      });



   }

   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

//   @UiHandler("table")
//   void onxclick(ClickEvent ce) {
//
//      Cell c = table.getCellForEvent(ce);
//
//
//      String id = table.getText(c.getRowIndex(), 0);
//
//
//      presenter.goToEdit(id);
//
//
//
//   }
   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
