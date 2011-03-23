package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.radoslavhusar.tapas.war.client.ui.MenuPresenter;
import com.radoslavhusar.tapas.war.client.ui.MenuViewImpl;
import com.radoslavhusar.tapas.war.client.tasks.TasksView;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditView;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditViewImpl;
import com.radoslavhusar.tapas.war.client.tasks.TasksViewImpl;
import com.radoslavhusar.tapas.war.client.ui.StatusPresenter;
import com.radoslavhusar.tapas.war.client.ui.StatusViewImpl;

public class GinClientModule extends AbstractGinModule {

   @Override
   protected void configure() {

      // Shared basic components
      bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
      bind(ClientState.class).in(Singleton.class);

      // Menu View+Presenters
      bind(MenuPresenter.class).in(Singleton.class);
      bind(StatusPresenter.class).in(Singleton.class);
      bind(MenuViewImpl.class).in(Singleton.class);
      bind(StatusViewImpl.class).in(Singleton.class);

      // Views
      bind(TaskEditView.class).to(TaskEditViewImpl.class).in(Singleton.class);
      bind(TasksView.class).to(TasksViewImpl.class).in(Singleton.class);
   }
}
