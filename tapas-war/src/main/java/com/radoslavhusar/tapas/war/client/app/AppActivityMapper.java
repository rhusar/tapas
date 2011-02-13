package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.radoslavhusar.tapas.war.client.app.HelloMVP;
import com.radoslavhusar.tapas.war.client.tasks.TaskListPlace;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditPlace;

public class AppActivityMapper implements ActivityMapper {

//   private ClientFactory clientFactory;

   /**
    * AppActivityMapper associates each Place with its corresponding  {@link Activity}
    *
    * @param clientFactory Factory to be passed to activities
    */
//   @Inject
//   public AppActivityMapper(ClientFactory clientFactory) {
//      super();
//      this.clientFactory = clientFactory;
//   }
   /**
    * Map each Place to its corresponding Activity. This would be a great use for GIN.
    * 
    * @return Activity
    */
   @Override
   public Activity getActivity(Place place) {
      // This is begging for GIN

      //return new TaskListActivity((TaskListPlace) place, clientFactory);

      System.out.println("returning new instance of " + place);

      if (place instanceof TaskListPlace) {
         return HelloMVP.getInjector().getTaskListActivity();
//         return new TaskListActivity();
      } else if (place instanceof TaskEditPlace) {
         return HelloMVP.getInjector().getTaskEditActivity();
//         return new TaskEditActivity();
      }


      /*if (place instanceof HelloPlace) {
      return new HelloActivity((HelloPlace) place, clientFactory);
      } else if (place instanceof GoodbyePlace) {
      return new GoodbyeActivity((GoodbyePlace) place, clientFactory);
      }*/

      return null;
   }
}
