package com.radoslavhusar.tapas.war.client.app;

import com.google.inject.Singleton;
import com.radoslavhusar.tapas.ejb.entity.Project;

/**
 * Singleton which controls state and cache of the application.
 */
@Singleton
public class ClientState {

   private Project project;

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }
}
