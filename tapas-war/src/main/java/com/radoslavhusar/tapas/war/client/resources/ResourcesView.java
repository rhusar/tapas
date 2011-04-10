package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.user.client.ui.IsWidget;
import com.radoslavhusar.tapas.war.client.ui.Bindable;

public interface ResourcesView extends IsWidget, Bindable {

   void setPresenter(Presenter presenter);

   public interface Presenter {

      public void setUnsaved(boolean b);
   }
}
