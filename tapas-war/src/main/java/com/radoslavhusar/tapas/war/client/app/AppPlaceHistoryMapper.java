package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.radoslavhusar.tapas.war.client.overview.OverviewPlace;
import com.radoslavhusar.tapas.war.client.projects.ProjectsPlace;
import com.radoslavhusar.tapas.war.client.resources.ResourcesPlace;
import com.radoslavhusar.tapas.war.client.tasks.TasksPlace;
import com.radoslavhusar.tapas.war.client.task.edit.TaskEditPlace;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers({
   ProjectsPlace.Tokenizer.class,
   OverviewPlace.Tokenizer.class,
   TasksPlace.Tokenizer.class,
   ResourcesPlace.Tokenizer.class,
   TaskEditPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
   // Nothing needed here.
}
