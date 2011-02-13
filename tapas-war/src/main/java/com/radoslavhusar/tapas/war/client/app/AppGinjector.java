package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.radoslavhusar.tapas.war.client.ui.MenuActivity;
import com.radoslavhusar.tapas.war.client.ui.MenuViewImpl;
import com.radoslavhusar.tapas.war.client.ui.StatusViewImpl;
import com.radoslavhusar.tapas.war.client.tasks.TaskListActivity;
import com.radoslavhusar.tapas.war.client.tasks.TaskListViewImpl;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditActivity;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditViewImpl;

@GinModules(GinClientModule.class)
public interface AppGinjector extends Ginjector {

   EventBus getEventBus();

   PlaceControllerGin getPlaceControllerGin();

   MenuActivity getMenuActivity();

   MenuViewImpl getMenuView();

   StatusViewImpl getStatusView();

   TaskListActivity getTaskListActivity();

   TaskListViewImpl getTaskListView();

   TaskEditActivity getTaskEditActivity();

   TaskEditViewImpl getTaskEditView();

}
