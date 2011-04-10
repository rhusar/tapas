package com.radoslavhusar.tapas.war.client.overview;

import com.google.gwt.user.client.ui.IsWidget;
import com.radoslavhusar.tapas.war.client.ui.Bindable;

public interface OverviewView extends IsWidget, Bindable {

   void setPresenter(OverviewPresenter presenter);
}
