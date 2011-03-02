package com.radoslavhusar.tapas.war.client.app;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Singleton;
import com.radoslavhusar.tapas.ejb.entity.Project;
import java.util.List;

/**
 * Singleton which controls state and cache of the application.
 */
@Singleton
public class ClientState {

   boolean synced = false;

   public ClientState() {
      Application.getInjector().getMyResourceService().findAllProjects(new AsyncCallback<List<Project>>() {

         @Override
         public void onFailure(Throwable caught) {
            synced = true;
         }

         @Override
         public void onSuccess(List<Project> result) {
            project = result.get(0);
            synced = true;
         }
      });

//      while (!synced) {
//         GWT.log("waiting");
//      }
   }
   private Project project;

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }
}
