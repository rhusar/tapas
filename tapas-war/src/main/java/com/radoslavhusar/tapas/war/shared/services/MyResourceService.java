package com.radoslavhusar.tapas.war.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.radoslavhusar.tapas.ejb.entity.Employee;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Task;
import java.util.List;

@RemoteServiceRelativePath("res")
public interface MyResourceService extends RemoteService {

   List<Project> findAllProjects();

   List<Employee> getResourcesForProject(Project project) throws Exception;

   int getCount() throws Exception;

   void create(Task task);

   void edit(Task task);

   void remove(Task task);

   Task find(long id);

   List<Task> findAll();

   List<Task> findRange(int[] range);
}
