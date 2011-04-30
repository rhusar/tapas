package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.radoslavhusar.tapas.war.client.projects.ProjectsPlace;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

   private Place defaultPlace = new ProjectsPlace();
   // TODO: When upgrade to 2.3 fix http://code.google.com/p/google-web-toolkit/issues/detail?id=5501
   private SimplePanel appWidget = new SimplePanel();
   private static final AppGinjector injector = GWT.create(AppGinjector.class);

   public interface GlobalResources extends ClientBundle {

      @NotStrict
      @Source("AppGlobal.css")
      CssResource css();
   }

   /**
    * This is the entry point method.
    */
   @Override
   public void onModuleLoad() {
      GWT.<GlobalResources>create(GlobalResources.class).css().ensureInjected();

      EventBus eventBus = injector.getEventBus();
      PlaceController placeController = injector.getPlaceController();

      // Start ActivityManager for the main widget with our ActivityMapper
      ActivityMapper activityMapper = new AppActivityMapper();
      ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
      activityManager.setDisplay(appWidget);

      // Start PlaceHistoryHandler with our PlaceHistoryMapper
      AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
      PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
      historyHandler.register(placeController, eventBus, defaultPlace);

      Window.enableScrolling(false);
      Window.setMargin("0px");
      Window.setTitle("TAPAS | Task-Resource Allocation System");

      RootLayoutPanel.get().add(appWidget);

      // Goes to place represented on URL or default place
      historyHandler.handleCurrentHistory();
   }

   /**
    * Static method for getting Application-scope Ginjector.
    *
    * @return app's Ginjector
    */
   public static AppGinjector getInjector() {
      return injector;
   }
}
