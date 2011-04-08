package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Task;
import java.util.List;

public interface TaskFacadeLocal {

   // CRUD
   void create(Task task);

   void edit(Task task);

   void editOrCreate(Task entity);

   void remove(Task task);

   Task find(Object id);

   List<Task> findAll();

   List<Task> findRange(int[] range);

   int count();

   // NON-CRUD
   List<Task> findAllForProject(long projectId);
}
