package com.radoslavhusar.tapas.war.client.reports;

import com.google.gwt.event.shared.GwtEvent;
import com.radoslavhusar.tapas.ejb.entity.Task;

public class TaskEditEvent extends GwtEvent<TaskEditEventHandler> {

   public static final Type<TaskEditEventHandler> TYPE = new Type<TaskEditEventHandler>();
   private Task task;

   public TaskEditEvent(Task task) {
      this.task = task;
   }

   public Task getTask() {
      return task;
   }

   @Override
   public Type<TaskEditEventHandler> getAssociatedType() {
      return TYPE;
   }

   @Override
   protected void dispatch(TaskEditEventHandler handler) {
      handler.onEditTask(this);
   }
}
