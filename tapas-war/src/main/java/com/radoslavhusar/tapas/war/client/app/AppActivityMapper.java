package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.radoslavhusar.tapas.war.client.overview.OverviewPlace;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditActivity;
import com.radoslavhusar.tapas.war.client.tasks.TasksPlace;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditPlace;

public class AppActivityMapper implements ActivityMapper {

   boolean doneInitialRedir = false;

   /**
    * Maps each Place to its corresponding Activity.
    * 
    * @return Activity
    * @param place a Place object
    */
   @Override
   public Activity getActivity(Place place) {

      if (place instanceof OverviewPlace) {
//         GWT.log(place.toString());
         return Application.getInjector().getOverviewActivity();
      } else if (place instanceof TasksPlace) {
         return Application.getInjector().getTaskListActivity();
      } else if (place instanceof TaskEditPlace) {

         // Dont make this singleton..
         return new TaskEditActivity(place);
      }

      return null;
   }
}
