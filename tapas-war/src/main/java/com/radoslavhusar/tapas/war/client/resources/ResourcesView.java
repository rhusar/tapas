package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.user.client.ui.IsWidget;
import com.radoslavhusar.tapas.ejb.entity.Trait;

public interface ResourcesView extends IsWidget {

   void setPresenter(Presenter presenter);

   public interface Presenter {

      public void setUnsaved(boolean b);
   }
}
