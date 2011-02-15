package com.radoslavhusar.tapas.war.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.radoslavhusar.tapas.ejb.entity.Employee;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.war.shared.services.MyResourceService;
import java.util.LinkedList;
import java.util.List;

public class MyResourceServiceImpl extends RemoteServiceServlet implements MyResourceService {

   private static final long serialVersionUID = 1L;

   @Override
   public List<Employee> getResourcesForProject(Project project) throws Exception {
      List e = new LinkedList();

      e.add(new Employee(1, "Radoslav", "Husar", null));
      e.add(new Employee(1, "Falsodar", "Husar", null));
      e.add(new Employee(1, "Michal", "Husar", null));

      return e;
   }
}
