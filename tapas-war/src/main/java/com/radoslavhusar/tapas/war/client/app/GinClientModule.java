package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.radoslavhusar.tapas.war.client.ui.MenuActivity;
import com.radoslavhusar.tapas.war.client.ui.MenuViewImpl;
import com.radoslavhusar.tapas.war.client.tasks.TaskListView;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditView;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditViewImpl;
import com.radoslavhusar.tapas.war.client.tasks.TaskListViewImpl;

public class GinClientModule extends AbstractGinModule {

   @Override
   protected void configure() {

//      bind(PlaceController.class).to(PlaceControllerGin.class).in(Singleton.class);
      bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
//      bind(PlaceController.class).PlaceControllerGin.class).in(Singleton.class);

//      bind(MyResourceService.class).(MyResourceService.class).in(Singleton.class);

      bind(MenuActivity.class).in(Singleton.class);
      bind(MenuViewImpl.class).in(Singleton.class);
      bind(TaskEditView.class).to(TaskEditViewImpl.class).in(Singleton.class);
      bind(TaskListView.class).to(TaskListViewImpl.class).in(Singleton.class);
   }
}
