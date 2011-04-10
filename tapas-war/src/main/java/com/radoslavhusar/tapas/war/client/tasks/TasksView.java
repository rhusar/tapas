package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.user.client.ui.IsWidget;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.war.client.ui.Bindable;
import java.util.Set;

public interface TasksView extends IsWidget, Bindable {

   void setPresenter(Presenter presenter);

   void redrawTasksTable();

   public interface Presenter {

      void doSaveTasks(Set<Task> changed);
   }
}
