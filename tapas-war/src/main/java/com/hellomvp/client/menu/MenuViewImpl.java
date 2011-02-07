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
package com.hellomvp.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
public class MenuViewImpl extends Composite implements MenuView {

   private static MenuViewImplUiBinder uiBinder = GWT.create(MenuViewImplUiBinder.class);
   private Presenter presenter;

   interface MenuViewImplUiBinder extends UiBinder<Widget, MenuViewImpl> {
   }
   @UiField
   Anchor signOutLink;
   @UiField
   Anchor aboutLink;

   public MenuViewImpl() {
      initWidget(uiBinder.createAndBindUi(this));
   }

   @UiHandler("aboutLink")
   void onSelectMeAnchorClick(ClickEvent event) {
      //Window.alert("clicked on selectMe");
      presenter.doAbout();
   }

   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
