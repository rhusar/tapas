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
import com.radoslavhusar.tapas.war.client.tasks.TasksPlace;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

   private Place defaultPlace = new TasksPlace();
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
      Window.setTitle("TAPAS | Task-Resource Allocation Platform");

      RootLayoutPanel.get().add(appWidget);

      /*
      // handlers
      eventBus.addHandler(TaskEditEvent.TYPE, new TaskEditEventHandler() {

      @Override
      public void onEditTask(TaskEditEvent event) {
      System.out.println(this.getClass().getName() + "is handling " + event);
      }
      });


      injector.getMyResourceService().getCount(new AsyncCallback<Integer>() {

      @Override
      public void onFailure(Throwable caught) {
      //            throw new UnsupportedOperationException("Not supported yet.");
      Window.alert("Request failed :-( ");
      }

      @Override
      public void onSuccess(Integer result) {
      //            System.out.println("IT WORKED!!! " + result);
      Window.alert("IT WORKED!!! " + result);
      }
      });*/

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
