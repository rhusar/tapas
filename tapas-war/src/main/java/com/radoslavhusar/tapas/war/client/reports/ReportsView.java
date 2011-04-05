package com.radoslavhusar.tapas.war.client.reports;

import com.google.gwt.user.client.ui.IsWidget;
import com.radoslavhusar.tapas.ejb.entity.Task;

public interface ReportsView extends IsWidget {

   void setPresenter(Presenter presenter);

   public void bind();

   public void unbind();

   public void setTask(Long integer);

   public interface Presenter {

      void goTo();

      public void doSubmit(Task t);
   }
}
