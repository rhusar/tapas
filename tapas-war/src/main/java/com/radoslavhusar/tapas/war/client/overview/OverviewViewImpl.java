package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.app.ClientState;

public class OverviewViewImpl extends ResizeComposite implements OverviewView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, OverviewViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField(provided = true)
   CellTable phases = new CellTable<ProjectPhase>();
   ListDataProvider<ProjectPhase> phasesProvider;

   @Inject
   public OverviewViewImpl(ClientState state) {
      phasesProvider = new ListDataProvider<ProjectPhase>();
      phases = new CellTable<ProjectPhase>(phasesProvider);

      initWidget(binder.createAndBindUi(this));

/*
      TextColumn<Task> idColumn = new TextColumn<Task>() {

         @Override
         public String getValue(Task task) {
            return "" + task.getId();
         }
      };

      phasesTable.addColumn(idColumn, "ID");
      phasesTable.addColumnStyleName(1, ".idColumnStyle");
      phasesTable.setColumnWidth(idColumn, 2, Unit.EM);

      TextColumn<Task> nameColumn = new TextColumn<Task>() {

         @Override
         public String getValue(Task task) {
            return task.getSummary();
         }
      };
      phasesTable.addColumn(nameColumn, "Summary");



      // Add a selection model to handle user selection.
      final SingleSelectionModel<Task> selectionModel = new SingleSelectionModel<Task>();
      phasesTable.setSelectionModel(selectionModel);
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

      phasesTable.setPageSize(Integer.MAX_VALUE - 1);


      Application.getInjector().getMyResourceService().findAll(new AsyncCallback<List<Task>>() {

         @Override
         public void onFailure(Throwable caught) {
            throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
         public void onSuccess(List<Task> result) {
//            List<Task> s = new ArrayList(result);
            phasesProvider.setList(result);

            // Set the total row count. This isn't strictly necessary, but it affects
            // paging calculations, so its good habit to keep the row count up to date.
            phasesTable.setRowCount(result.size(), true);

            // Push the data into the widget.
            phasesTable.setRowData(0, result);
         }
      });
*/

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
