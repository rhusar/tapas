package com.hellomvp.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.hellomvp.client.menu.MenuActivity;
import com.hellomvp.client.menu.MenuView;
import com.hellomvp.client.menu.MenuViewImpl;
import com.hellomvp.client.task.TaskListView;
import com.hellomvp.client.task.TaskListViewImpl;
import com.hellomvp.client.task.edit.TaskEditView;
import com.hellomvp.client.task.edit.TaskEditViewImpl;
import com.hellomvp.client.ui.GoodbyeView;
import com.hellomvp.client.ui.GoodbyeViewImpl;
import com.hellomvp.client.ui.HelloView;
import com.hellomvp.client.ui.HelloViewImpl;

public class ClientFactoryImpl implements ClientFactory {

   // MVP
   private static final EventBus eventBus = new SimpleEventBus();
   private static final PlaceController placeController = new PlaceController(eventBus);
   // Menu/Status
   private static final MenuActivity menua = new MenuActivity(eventBus);
   private static final MenuViewImpl menu = new MenuViewImpl(menua);
   private static final HelloView helloView = new HelloViewImpl();
   private static final GoodbyeView goodbyeView = new GoodbyeViewImpl();
   //private static final TaskEditView tev = new TaskEditViewImpl(menu);
   private static final TaskListViewImpl taskListView = new TaskListViewImpl(menu);

   @Override
   public EventBus getEventBus() {
      return eventBus;
   }

   @Override
   public HelloView getHelloView() {
      return helloView;
   }

   @Override
   public PlaceController getPlaceController() {
      return placeController;
   }

   @Override
   public GoodbyeView getGoodbyeView() {
      return goodbyeView;
   }

   @Override
   public TaskListView getTaskListView() {
      return new TaskListViewImpl(menu);
   }

   @Override
   public TaskEditView getTaskEditView() {
      return new TaskEditViewImpl(menu);
   }
}
