package com.radoslavhusar.tapas.war.shared.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.radoslavhusar.tapas.ejb.entity.Employee;

public interface EmployeeServiceGWTAsync {
	void deleteEmployee(long employeeId, AsyncCallback<Void> callback);

	void findEmployee(long employeeId, AsyncCallback<Employee> callback);

	void saveEmployee(long employeeId, String name, String surname,
			String jobDescription, AsyncCallback<Void> callback);

	void saveOrUpdateEmployee(long employeeId, String name, String surname,
			String jobDescription, AsyncCallback<Void> callback);

	void updateEmployee(long employeeId, String name, String surname,
			String jobDescription, AsyncCallback<Void> callback);
}
