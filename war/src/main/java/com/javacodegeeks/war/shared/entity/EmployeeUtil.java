package com.javacodegeeks.war.shared.entity;

import java.io.Serializable;

public class EmployeeUtil implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2732740011239267035L;

	private long employeeId;
	
	private String employeeName;
	
	private String employeeSurname;
	
	private String job;
	
	
	public EmployeeUtil() {
	}

	public EmployeeUtil(int employeeId) {
		this.employeeId = employeeId;		
	}

	public EmployeeUtil(long employeeId, String employeeName, String employeeSurname,
			String job) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeSurname = employeeSurname;
		this.job = job;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeSurname() {
		return employeeSurname;
	}

	public void setEmployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	


}
