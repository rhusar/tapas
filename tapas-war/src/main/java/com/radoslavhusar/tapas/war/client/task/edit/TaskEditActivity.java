package com.radoslavhusar.tapas.war.client.task.edit;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.war.client.app.HelloMVP;
import com.radoslavhusar.tapas.war.shared.services.TaskListDummySource;
import com.radoslavhusar.tapas.war.client.tasks.TaskListPlace;

public class TaskEditActivity extends AbstractActivity implements TaskEditView.Presenter {

   private TaskEditView view;
   private TaskEditPlace place;

   public TaskEditActivity(Place place) {
      this.place = (TaskEditPlace) place;
   }

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      view = HelloMVP.getInjector().getTaskEditView();
      view.setPresenter(this);
      view.bind();
      panel.setWidget(view.asWidget());
//      Place o = HelloMVP.getInjector().getPlaceController().getWhere();
//
//      if (o.equals(Place.NOWHERE)) {
//         return;
//      }

      //TaskEditPlace tep = (TaskEditPlace) HelloMVP.getInjector().getPlaceController().getWhere();
      Integer taskId = Integer.valueOf(place.getTaskId());

      Task e = null;
      for (Task t : TaskListDummySource.fetch()) {
         if (t.getId() == taskId) {
            e = t;
            break;
         }
      }

      System.out.println("editing " + e.getId());

   }

   @Override
   public String mayStop() {
      System.out.println(HelloMVP.getInjector().getPlaceController().getWhere().equals(Place.NOWHERE));
      return super.mayStop();
   }

   @Override
   public void onStop() {
      System.out.println(HelloMVP.getInjector().getPlaceController().getWhere().equals(Place.NOWHERE));
   }

   @Override
   public void goTo() {
      view.unbind();
      HelloMVP.getInjector().getPlaceController().goTo(new TaskListPlace());
   }

   @Override
   public void save(Task t) {
      System.out.println("saving task: " + t.toString());
      //HelloMVP.getInjector().getEventBus().fireEvent(new TaskEditEvent(t));
      HelloMVP.getInjector().getMyResourceService().create(t, new AsyncCallback<Void>() {

         @Override
         public void onFailure(Throwable caught) {
            Window.alert("error while saving!");
         }

         @Override
         public void onSuccess(Void result) {
            Window.alert("SAVED!");
         }
      });
   }
}
