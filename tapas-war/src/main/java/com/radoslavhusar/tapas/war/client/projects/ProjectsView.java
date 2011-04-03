package com.radoslavhusar.tapas.war.client.projects;

import com.google.gwt.user.client.ui.IsWidget;
import com.radoslavhusar.tapas.ejb.entity.Trait;

public interface ProjectsView extends IsWidget {

   void setPresenter(Presenter presenter);

   void bind();

   void unbind();

   public interface Presenter {

      void saveTrait(Trait trait);
   }
}
