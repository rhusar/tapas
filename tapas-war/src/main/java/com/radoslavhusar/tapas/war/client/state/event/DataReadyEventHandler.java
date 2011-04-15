package com.radoslavhusar.tapas.war.client.state.event;

import com.google.gwt.event.shared.EventHandler;

public interface DataReadyEventHandler extends EventHandler {

   void onDataReady(DataReadyEvent event);
}
