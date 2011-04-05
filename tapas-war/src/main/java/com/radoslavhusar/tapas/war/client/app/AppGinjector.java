package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.radoslavhusar.tapas.war.client.overview.OverviewActivity;
import com.radoslavhusar.tapas.war.client.overview.OverviewViewImpl;
import com.radoslavhusar.tapas.war.client.planning.PlanningActivity;
import com.radoslavhusar.tapas.war.client.planning.PlanningViewImpl;
import com.radoslavhusar.tapas.war.client.projects.ProjectsActivity;
import com.radoslavhusar.tapas.war.client.projects.ProjectsViewImpl;
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

   // Services - must be also defined in Application.gwt.xml and web.xml
   TaskResourceServiceAsync getService();

   // Presenters + Activities
   MenuPresenter getMenuPresenter();

   StatusPresenter getStatusPresenter();

   ProjectsActivity getProjectsActivity();

   OverviewActivity getOverviewActivity();

   TasksActivity getTaskListActivity();

   ResourcesActivity getResourcesActivity();

   PlanningActivity getPlanningActivity();

   // Views - UI
   MenuViewImpl getMenuView();

   StatusViewImpl getStatusView();

   // Views - activities
   ProjectsViewImpl getProjectsView();

   OverviewViewImpl getOverviewView();

   TasksViewImpl getTaskListView();

   ResourcesViewImpl getResourcesView();

   TaskEditViewImpl getTaskEditView();

   PlanningViewImpl getPlanningView();
}
