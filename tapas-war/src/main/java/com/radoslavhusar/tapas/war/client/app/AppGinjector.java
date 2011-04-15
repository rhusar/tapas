package com.radoslavhusar.tapas.war.client.app;

import com.radoslavhusar.tapas.war.client.state.ClientState;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.radoslavhusar.tapas.war.client.overview.OverviewActivity;
import com.radoslavhusar.tapas.war.client.planning.PlanningActivity;
import com.radoslavhusar.tapas.war.client.projects.ProjectsActivity;
import com.radoslavhusar.tapas.war.client.resources.ResourcesActivity;
import com.radoslavhusar.tapas.war.client.menu.MenuPresenter;
import com.radoslavhusar.tapas.war.client.menu.MenuView;
import com.radoslavhusar.tapas.war.client.tasks.TasksActivity;
import com.radoslavhusar.tapas.war.client.menu.StatusPresenter;
import com.radoslavhusar.tapas.war.client.menu.StatusView;
import com.radoslavhusar.tapas.war.client.overview.OverviewView;
import com.radoslavhusar.tapas.war.client.planning.PlanningView;
import com.radoslavhusar.tapas.war.client.projects.ProjectsView;
import com.radoslavhusar.tapas.war.client.reports.ReportsView;
import com.radoslavhusar.tapas.war.client.resources.ResourcesView;
import com.radoslavhusar.tapas.war.client.tasks.TasksView;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceServiceAsync;

@GinModules(AppGinModule.class)
public interface AppGinjector extends Ginjector {

   // Shared basic components
   EventBus getEventBus();

   AppPlaceController getPlaceController();

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
   MenuView getMenuView();

   StatusView getStatusView();

   // Views - activities
   ProjectsView getProjectsView();

   OverviewView getOverviewView();

   TasksView getTaskListView();

   ResourcesView getResourcesView();

   ReportsView getTaskEditView();

   PlanningView getPlanningView();
}
