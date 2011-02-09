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
package com.hellomvp.client.task;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.hellomvp.client.ClientFactory;
import com.hellomvp.client.task.edit.TaskEditPlace;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
public class TaskListActivity extends AbstractActivity implements TaskListView.Presenter {

   private final ClientFactory clientFactory;

   public TaskListActivity(Place taskListPlace, ClientFactory clientFactory) {
      this.clientFactory = clientFactory;
   }

   @Override
   public void start(AcceptsOneWidget panel, EventBus eventBus) {
      TaskListView view = clientFactory.getTaskListView();
      view.setPresenter(this);

      panel.setWidget(view.asWidget());
   }

   @Override
   public void goToEdit(String someId) {
      clientFactory.getPlaceController().goTo(new TaskEditPlace(someId));
   }
}
