package com.radoslavhusar.tapas.war.client.projects;

import com.google.gwt.user.client.ui.IsWidget;
import com.radoslavhusar.tapas.ejb.entity.Trait;
import com.radoslavhusar.tapas.war.client.ui.Bindable;

public interface ProjectsView extends IsWidget, Bindable {

   void setPresenter(Presenter presenter);

   public interface Presenter {

      void saveTrait(Trait trait);
   }
}
