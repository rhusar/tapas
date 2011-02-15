package com.radoslavhusar.tapas.war.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.radoslavhusar.tapas.ejb.entity.Employee;
import com.radoslavhusar.tapas.ejb.entity.Project;
import java.util.List;

@RemoteServiceRelativePath("res")
public interface MyResourceService  extends RemoteService {

   public List<Employee> getResourcesForProject(Project project) throws Exception;
}
