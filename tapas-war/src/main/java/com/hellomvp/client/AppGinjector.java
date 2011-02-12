package com.hellomvp.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.hellomvp.client.menu.MenuActivity;
import com.hellomvp.client.task.TaskListActivity;
import com.hellomvp.client.task.TaskListView;
import com.hellomvp.client.task.edit.TaskEditActivity;
import com.hellomvp.client.task.edit.TaskEditView;

@GinModules(GinClientModule.class)
public interface AppGinjector extends Ginjector {

   EventBus getEventBus();

   PlaceControllerGin getPlaceControllerGin();

   MenuActivity getMenuActivity();

   TaskListActivity getTaskListActivity();

   TaskListView getTaskListView();

   TaskEditActivity getTaskEditActivity();

   TaskEditView getTaskEditView();
}
