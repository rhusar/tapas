package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.stats.ProjectStats;
import java.util.List;

public interface ProjectFacadeLocal {

   // CRUD
   void create(Project project);

   void edit(Project project);

   void editOrCreate(Project project);

   void remove(Project project);

   Project find(Object id);

   List<Project> findAll();

   List<Project> findRange(int[] range);

   int count();

   // CUSTOM
   ProjectStats tallyProjectStats(long projectId);
}
