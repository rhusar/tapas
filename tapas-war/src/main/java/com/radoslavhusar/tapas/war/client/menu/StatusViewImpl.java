package com.radoslavhusar.tapas.war.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StatusViewImpl extends Composite implements StatusView {

   private static Binder binder = GWT.create(Binder.class);

   @Override
   public void setPresenter(Presenter presenter) {
   }

   interface Binder extends UiBinder<Widget, StatusViewImpl> {
   }

   public StatusViewImpl() {
      initWidget(binder.createAndBindUi(this));
   }
}
