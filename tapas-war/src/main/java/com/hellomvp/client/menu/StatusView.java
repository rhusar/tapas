package com.hellomvp.client.menu;

import com.google.gwt.user.client.ui.IsWidget;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
public interface StatusView extends IsWidget {

   void setPresenter(Presenter presenter);

   public interface Presenter {
 
   }
}
