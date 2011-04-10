package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.radoslavhusar.tapas.war.client.projects.ProjectsView;
import com.radoslavhusar.tapas.war.client.projects.ProjectsViewImpl;
import com.radoslavhusar.tapas.war.client.menu.MenuPresenter;
import com.radoslavhusar.tapas.war.client.menu.MenuView;
import com.radoslavhusar.tapas.war.client.menu.MenuViewImpl;
import com.radoslavhusar.tapas.war.client.tasks.TasksView;
import com.radoslavhusar.tapas.war.client.reports.ReportsView;
import com.radoslavhusar.tapas.war.client.reports.ReportsViewImpl;
import com.radoslavhusar.tapas.war.client.tasks.TasksViewImpl;
import com.radoslavhusar.tapas.war.client.menu.StatusPresenter;
import com.radoslavhusar.tapas.war.client.menu.StatusView;
import com.radoslavhusar.tapas.war.client.menu.StatusViewImpl;
import com.radoslavhusar.tapas.war.client.overview.OverviewView;
import com.radoslavhusar.tapas.war.client.overview.OverviewViewImpl;
import com.radoslavhusar.tapas.war.client.planning.PlanningView;
import com.radoslavhusar.tapas.war.client.planning.PlanningViewImpl;
import com.radoslavhusar.tapas.war.client.resources.ResourcesView;
import com.radoslavhusar.tapas.war.client.resources.ResourcesViewImpl;

public class GinClientModule extends AbstractGinModule {

   @Override
   protected void configure() {

      // Shared basic components
      bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
      bind(ClientState.class).in(Singleton.class);

      // Menu View+Presenters
      bind(MenuPresenter.class).in(Singleton.class);
      bind(StatusPresenter.class).in(Singleton.class);
      bind(MenuView.class).to(MenuViewImpl.class).in(Singleton.class);
      bind(StatusView.class).to(StatusViewImpl.class).in(Singleton.class);

      // Views
      bind(OverviewView.class).to(OverviewViewImpl.class).in(Singleton.class);
      bind(PlanningView.class).to(PlanningViewImpl.class).in(Singleton.class);
      bind(ProjectsView.class).to(ProjectsViewImpl.class).in(Singleton.class);
      bind(ReportsView.class).to(ReportsViewImpl.class).in(Singleton.class);
      bind(ResourcesView.class).to(ResourcesViewImpl.class).in(Singleton.class);
      bind(TasksView.class).to(TasksViewImpl.class).in(Singleton.class);

      // No need to bind Activies - they are already implementations.

   }
}
