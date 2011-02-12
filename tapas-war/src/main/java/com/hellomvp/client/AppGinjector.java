package com.hellomvp.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.hellomvp.client.menu.MenuActivity;
import com.hellomvp.client.menu.MenuViewImpl;
import com.hellomvp.client.task.TaskListActivity;
import com.hellomvp.client.task.TaskListViewImpl;
import com.hellomvp.client.task.edit.TaskEditActivity;
import com.hellomvp.client.task.edit.TaskEditViewImpl;

@GinModules(GinClientModule.class)
public interface AppGinjector extends Ginjector {

   EventBus getEventBus();

   PlaceControllerGin getPlaceControllerGin();

   MenuActivity getMenuActivity();

   MenuViewImpl getMenuView();

   TaskListActivity getTaskListActivity();

   TaskListViewImpl getTaskListView();

   TaskEditActivity getTaskEditActivity();

   TaskEditViewImpl getTaskEditView();
}
