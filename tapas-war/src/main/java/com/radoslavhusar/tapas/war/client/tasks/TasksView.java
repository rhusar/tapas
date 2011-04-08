package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.user.client.ui.IsWidget;
import com.radoslavhusar.tapas.ejb.entity.Task;
import java.util.Set;

public interface TasksView extends IsWidget {

   void setPresenter(Presenter presenter);

   void redrawTasksTable();

   public interface Presenter {

      void doSaveTasks(Set<Task> changed);
   }
}
