package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.radoslavhusar.tapas.war.client.overview.OverviewPlace;
import com.radoslavhusar.tapas.war.client.planning.PlanningPlace;
import com.radoslavhusar.tapas.war.client.projects.ProjectsPlace;
import com.radoslavhusar.tapas.war.client.resources.ResourcesPlace;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditActivity;
import com.radoslavhusar.tapas.war.client.tasks.TasksPlace;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditPlace;

public class AppActivityMapper implements ActivityMapper {

   /**
    * Maps each Place to its corresponding Activity.
    *
    * @param place a Place object
    * @return Activity
    */
   @Override
   public Activity getActivity(Place place) {

      if (place instanceof ProjectsPlace) {
         // No ID!
         return Application.getInjector().getProjectsActivity();
      } else if (place instanceof OverviewPlace) {
         Application.getInjector().getClientState().setProjectId(((OverviewPlace) place).getProjectId());
         return Application.getInjector().getOverviewActivity();
      } else if (place instanceof TasksPlace) {
         Application.getInjector().getClientState().setProjectId(((TasksPlace) place).getProjectId());
         return Application.getInjector().getTaskListActivity();
      } else if (place instanceof TaskEditPlace) {
         // Dont make this singleton.. or?
         return new TaskEditActivity(place);
      } else if (place instanceof ResourcesPlace) {
         // Let the client know which project
         Application.getInjector().getClientState().setProjectId(((ResourcesPlace) place).getProjectId());
         return Application.getInjector().getResourcesActivity();
      } else if (place instanceof PlanningPlace) {
         // Let the client know which project
         Application.getInjector().getClientState().setProjectId(((PlanningPlace) place).getProjectId());
         return Application.getInjector().getPlanningActivity();
      }

      return null;
   }
}
