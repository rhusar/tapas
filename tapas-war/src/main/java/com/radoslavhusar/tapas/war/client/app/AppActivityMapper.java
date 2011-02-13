package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.radoslavhusar.tapas.war.client.tasks.TaskListPlace;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditPlace;

public class AppActivityMapper implements ActivityMapper {

   /**
    * Maps each Place to its corresponding Activity.
    * 
    * @return Activity
    * @param place a Place object
    */
   @Override
   public Activity getActivity(Place place) {

      if (place instanceof TaskListPlace) {
         return HelloMVP.getInjector().getTaskListActivity();
      } else if (place instanceof TaskEditPlace) {
         return HelloMVP.getInjector().getTaskEditActivity();
      }

      return null;
   }
}
