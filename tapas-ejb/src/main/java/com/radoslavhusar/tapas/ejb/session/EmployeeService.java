package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Employee;

public interface EmployeeService {

   public Employee findEmployee(long employeeId);

   public void saveEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;

   public void updateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;

   public void saveOrUpdateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;

   public void deleteEmployee(long employeeId) throws Exception;
}
