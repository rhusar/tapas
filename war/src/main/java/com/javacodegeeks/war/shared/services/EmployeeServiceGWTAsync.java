package com.javacodegeeks.war.shared.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.javacodegeeks.war.shared.entity.EmployeeUtil;

public interface EmployeeServiceGWTAsync {
	void deleteEmployee(long employeeId, AsyncCallback<Void> callback);

	void findEmployee(long employeeId, AsyncCallback<EmployeeUtil> callback);

	void saveEmployee(long employeeId, String name, String surname,
			String jobDescription, AsyncCallback<Void> callback);

	void saveOrUpdateEmployee(long employeeId, String name, String surname,
			String jobDescription, AsyncCallback<Void> callback);

	void updateEmployee(long employeeId, String name, String surname,
			String jobDescription, AsyncCallback<Void> callback);
}
