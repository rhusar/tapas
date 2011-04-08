package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import java.util.List;

public interface ProjectPhaseFacadeLocal {

   // CRUD
   void create(ProjectPhase projectPhase);

   void edit(ProjectPhase projectPhase);

   void editOrCreate(ProjectPhase entity);

   void remove(ProjectPhase projectPhase);

   ProjectPhase find(Object id);

   List<ProjectPhase> findAll();

   List<ProjectPhase> findRange(int[] range);

   int count();
   // CUSTOM 
}
