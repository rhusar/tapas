package com.javacodegeeks.ejb.session;

import com.javacodegeeks.ejb.entity.Employee;


public interface EmployeeService {
	public Employee findEmployee(long employeeId);
	public void saveEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;
	public void updateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;
	public void saveOrUpdateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;
	public void deleteEmployee(long employeeId) throws Exception;
}
