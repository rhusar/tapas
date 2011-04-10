package com.radoslavhusar.tapas.war.client.planning;

import com.google.gwt.user.client.ui.IsWidget;
import com.radoslavhusar.tapas.war.client.ui.Bindable;

public interface PlanningView extends IsWidget, Bindable {

   void setPresenter(Presenter presenter);

   public interface Presenter {
   }
}
