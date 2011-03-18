package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.radoslavhusar.tapas.war.client.overview.OverviewActivity;
import com.radoslavhusar.tapas.war.client.overview.OverviewViewImpl;
import com.radoslavhusar.tapas.war.client.resources.ResourcesActivity;
import com.radoslavhusar.tapas.war.client.resources.ResourcesViewImpl;
import com.radoslavhusar.tapas.war.client.ui.MenuPresenter;
import com.radoslavhusar.tapas.war.client.ui.MenuViewImpl;
import com.radoslavhusar.tapas.war.client.ui.StatusViewImpl;
import com.radoslavhusar.tapas.war.client.tasks.TasksActivity;
import com.radoslavhusar.tapas.war.client.tasks.TasksViewImpl;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditViewImpl;
import com.radoslavhusar.tapas.war.client.ui.StatusPresenter;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;

@GinModules(GinClientModule.class)
public interface AppGinjector extends Ginjector {

   // Shared basic components
   EventBus getEventBus();

   PlaceControllerGin getPlaceController();

   ClientState getClientState();

   // Presenters + Activities
   MenuPresenter getMenuPresenter();

   StatusPresenter getStatusPresenter();

   TasksActivity getTaskListActivity();

   OverviewActivity getOverviewActivity();

   ResourcesActivity getResourcesActivity();

   // Views - UI
   MenuViewImpl getMenuView();

   StatusViewImpl getStatusView();

   // Views - activities
   OverviewViewImpl getOverviewView();

   TasksViewImpl getTaskListView();

   TaskEditViewImpl getTaskEditView();

   ResourcesViewImpl getResourcesView();

   // Services - must be also defined in Application.gwt.xml and web.xml
   TaskResourceServiceAsync getService();
}
