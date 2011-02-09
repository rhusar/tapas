package com.hellomvp.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.hellomvp.client.ClientFactory;
import com.hellomvp.client.task.TaskListActivity;
import com.hellomvp.client.task.TaskListPlace;
import com.hellomvp.client.task.edit.TaskEditActivity;
import com.hellomvp.client.task.edit.TaskEditPlace;

public class AppActivityMapper implements ActivityMapper {

   private ClientFactory clientFactory;

   /**
    * AppActivityMapper associates each Place with its corresponding  {@link Activity}
    *
    * @param clientFactory Factory to be passed to activities
    */
   public AppActivityMapper(ClientFactory clientFactory) {
      super();
      this.clientFactory = clientFactory;
   }

   /**
    * Map each Place to its corresponding Activity. This would be a great use for GIN.
    * 
    * @return Activity
    */
   @Override
   public Activity getActivity(Place place) {
      // This is begging for GIN

      //return new TaskListActivity((TaskListPlace) place, clientFactory);

      if (place instanceof TaskListPlace) {
         return new TaskListActivity(place, clientFactory);
      } else if (place instanceof TaskEditPlace) {
         return new TaskEditActivity(place, clientFactory);
      }
      

      /*if (place instanceof HelloPlace) {
         return new HelloActivity((HelloPlace) place, clientFactory);
      } else if (place instanceof GoodbyePlace) {
         return new GoodbyeActivity((GoodbyePlace) place, clientFactory);
      }*/

      return null;
   }
}
