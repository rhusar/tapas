package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.user.client.ui.IsWidget;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
public interface TasksView extends IsWidget {

   void setPresenter(Presenter presenter);

   public interface Presenter {

      void goToEdit(String someId);
   }
}
