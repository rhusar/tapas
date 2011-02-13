/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
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
import com.google.inject.Inject;
import com.radoslavhusar.tapas.war.client.app.HelloMVP;
import com.radoslavhusar.tapas.war.client.menu.ui.MenuViewImpl;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
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

      //this.menu = (MenuViewImpl) menu;
      initWidget(binder.createAndBindUi(this));

      //menu.setPresenter(him);
//      System.out.println("TaskEditViewImpl my menu is " + menu.getPresenter());


   }

   @Override
   public void bind() {
      menu.add(HelloMVP.getInjector().getMenuView());
      status.add(HelloMVP.getInjector().getStatusView());
   }

   public void unbind() {
      menu.clear();
      status.clear();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
   @UiField
   Anchor aaa;

   @UiHandler("aaa")
   void dasdasdasdas(ClickEvent ce) {
      presenter.goTo();
   }
}
