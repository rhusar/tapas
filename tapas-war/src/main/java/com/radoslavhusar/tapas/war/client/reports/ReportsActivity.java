package com.radoslavhusar.tapas.war.client.reports;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.war.client.app.Application;
import com.radoslavhusar.tapas.war.client.tasks.TasksPlace;

public class ReportsActivity extends AbstractActivity implements ReportsView.Presenter {

   private ReportsView view;
   private ReportsPlace place;

   public ReportsActivity(Place place) {
      this.place = (ReportsPlace) place;
   }

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      view = Application.getInjector().getTaskEditView();
      view.setPresenter(this);
      view.setTask(place.getTaskId() == null ? null : Long.valueOf(place.getTaskId()));
      view.bind();
      panel.setWidget(view.asWidget());
//      Place o = Application.getInjector().getPlaceController().getWhere();
//
//      if (o.equals(Place.NOWHERE)) {
//         return;
//      }

      //TaskEditPlace tep = (ReportsPlace) Application.getInjector().getPlaceController().getWhere();
//      Integer taskId = Integer.valueOf(place.getTaskId());

//      Task e = null;
//      for (Task t : TaskListDummySource.fetch()) {
//         if (t.getId() == taskId) {
//            e = t;
//            break;
//         }
//      }
//
//      System.out.println("editing " + e.getId());
   }

   @Override
   public String mayStop() {
//      System.out.println(Application.getInjector().getPlaceController().getWhere().equals(Place.NOWHERE));
      return super.mayStop();
   }

   @Override
   public void onStop() {
//      System.out.println(Application.getInjector().getPlaceController().getWhere().equals(Place.NOWHERE));
   }

   @Override
   public void goTo() {
      view.unbind();
      Application.getInjector().getPlaceController().goTo(new TasksPlace((long) 1));
   }

   @Override
   public void doSubmit(Task t) {
      GWT.log("Saving task: " + t.toString());

      //Application.getInjector().getEventBus().fireEvent(new TaskEditEvent(t));
/*      Application.getInjector().getService().edit(t, new AsyncCallback<Void>() {
      
      @Override
      public void onFailure(Throwable caught) {
      Window.alert("error while saving!");
      }
      
      @Override
      public void onSuccess(Void result) {
      //Window.alert("SAVED!");
      GWT.log("saved");
      }
      });*/

      Application.getInjector().getPlaceController().goTo(new TasksPlace((long) 1));
   }
}
