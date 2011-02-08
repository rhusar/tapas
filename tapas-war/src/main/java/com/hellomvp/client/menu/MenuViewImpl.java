
package com.hellomvp.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
public class MenuViewImpl extends Composite implements MenuView {

   private static Binder binder = GWT.create(Binder.class);
   private Presenter presenter;

   interface Binder extends UiBinder<Widget, MenuViewImpl> {
   }
   @UiField
   Anchor signOutLink;
//   @UiField
//   Anchor aboutLink;

   public MenuViewImpl() {
      initWidget(binder.createAndBindUi(this));
   }

   @UiHandler("signOutLink")
   void onSelectMeAnchorClick(ClickEvent event) {
      //Window.alert("clicked on selectMe");
      presenter.doAbout();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
