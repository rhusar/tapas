package com.javacodegeeks.war.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.javacodegeeks.war.shared.entity.EmployeeUtil;

@RemoteServiceRelativePath("emp")
public interface EmployeeServiceGWT  extends RemoteService  {
	public EmployeeUtil findEmployee(long employeeId);
	public void saveEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;
	public void updateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;
	public void saveOrUpdateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;
	public void deleteEmployee(long employeeId) throws Exception;
}
