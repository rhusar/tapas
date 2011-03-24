package com.radoslavhusar.tapas.war.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class DataReadyEvent extends GwtEvent<DataReadyEventHandler> {

   public static final Type<DataReadyEventHandler> TYPE = new Type<DataReadyEventHandler>();
   private DataType type;

   public static enum DataType {

      OVERVIEW, TASKS, RESOURCES, PROJECT, GROUPS
   }

   public DataReadyEvent(DataType dataType) {
      this.type = dataType;
   }

   public DataType getType() {
      return type;
   }

   @Override
   public Type<DataReadyEventHandler> getAssociatedType() {
      return TYPE;
   }

   @Override
   protected void dispatch(DataReadyEventHandler handler) {
      handler.onDataReady(this);
   }
}
