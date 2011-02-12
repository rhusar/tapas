package com.hellomvp.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.hellomvp.client.menu.MenuActivity;
import com.hellomvp.client.menu.MenuViewImpl;
import com.hellomvp.client.task.TaskListView;
import com.hellomvp.client.task.TaskListViewImpl;
import com.hellomvp.client.task.edit.TaskEditView;
import com.hellomvp.client.task.edit.TaskEditViewImpl;

public class GinClientModule extends AbstractGinModule {

   @Override
   protected void configure() {

      bind(PlaceControllerGin.class).to(PlaceControllerGin.class).in(Singleton.class);
      bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

      bind(MenuActivity.class).in(Singleton.class);
      bind(MenuViewImpl.class).in(Singleton.class);
      bind(TaskEditView.class).to(TaskEditViewImpl.class).in(Singleton.class);
      bind(TaskListView.class).to(TaskListViewImpl.class).in(Singleton.class);

   }
}