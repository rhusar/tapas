package com.radoslavhusar.tapas.war.server.services;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.radoslavhusar.tapas.ejb.entity.Employee;
import com.radoslavhusar.tapas.ejb.session.EmployeeService;
import com.radoslavhusar.tapas.war.shared.services.EmployeeServiceGWT;

public class EmployeeServiceGWTImpl extends RemoteServiceServlet implements EmployeeServiceGWT {

   private static final long serialVersionUID = 5995064321382986251L;
   @EJB //(mappedName = "EmployeeServiceBean/local")
   private EmployeeService employeeService;
   @Resource(name = "java:UserTransaction")
   UserTransaction ut;

   /*
   @EJB(mappedName = "employeeService/local")
   public void setEmployeeService(EmployeeService employeeService) {
   this.employeeService = employeeService;
   }*/
   @Override
   public void deleteEmployee(long employeeId) throws Exception {
      employeeService.deleteEmployee(employeeId);

   }

   @Override
   public Employee findEmployee(long employeeId) {

      Employee emp = employeeService.findEmployee(employeeId);
      if (emp != null) {
         try {
            ut.begin();
            Employee util = new Employee(emp.getEmployeeId(), emp.getEmployeeName(), emp.getEmployeeSurname(), emp.getJob());

            ut.commit();
            return util;
         } catch (Exception e) {
         }

      }
      return null;
   }

   public void saveEmployee(long employeeId, String name, String surname,
           String jobDescription) throws Exception {
      employeeService.saveEmployee(employeeId, name, surname, jobDescription);

   }

   public void saveOrUpdateEmployee(long employeeId, String name,
           String surname, String jobDescription) throws Exception {
      employeeService.saveOrUpdateEmployee(employeeId, name, surname, jobDescription);

   }

   public void updateEmployee(long employeeId, String name, String surname,
           String jobDescription) throws Exception {
      employeeService.updateEmployee(employeeId, name, surname, jobDescription);

   }
}
