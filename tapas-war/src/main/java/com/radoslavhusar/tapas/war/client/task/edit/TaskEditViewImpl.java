package com.radoslavhusar.tapas.war.client.task.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.radoslavhusar.tapas.war.client.app.HelloMVP;

public class TaskEditViewImpl extends ResizeComposite implements TaskEditView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, TaskEditViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;

   public TaskEditViewImpl() {
      initWidget(binder.createAndBindUi(this));
   }

   @Override
   public void bind() {
      menu.add(HelloMVP.getInjector().getMenuView());
      status.add(HelloMVP.getInjector().getStatusView());
   }

   @Override
   public void unbind() {
      menu.clear();
      status.clear();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
   @UiField
   Anchor cancel;

   @UiHandler("cancel")
   void cancelAndReturn(ClickEvent event) {
      presenter.goTo();
   }
}
