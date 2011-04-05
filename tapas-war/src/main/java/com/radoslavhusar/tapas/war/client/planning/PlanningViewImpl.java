package com.radoslavhusar.tapas.war.client.planning;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.radoslavhusar.tapas.war.client.app.Application;

public class PlanningViewImpl extends ResizeComposite implements PlanningView {

   private Presenter presenter;
   private static Binder binder = GWT.create(Binder.class);

   interface Binder extends UiBinder<Widget, PlanningViewImpl> {
   }
   @UiField
   SimplePanel menu;
   @UiField
   SimplePanel status;
   @UiField
   VerticalPanel content;

   public PlanningViewImpl() {
      initWidget(binder.createAndBindUi(this));
      content.setSpacing(5);
      GWT.log("New PlanningViewImpl created.");
   }

   // UI routines
   public void bind() {
      menu.add(Application.getInjector().getMenuView());
      status.add(Application.getInjector().getStatusView());
      
      //content.add(new heading("a"));
      content.add(new HTML("<h1>Planning</h1>"));
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
