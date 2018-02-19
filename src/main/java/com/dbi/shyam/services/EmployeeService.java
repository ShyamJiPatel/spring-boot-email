package com.dbi.shyam.services;

import com.dbi.shyam.entities.Employee;

public interface EmployeeService {

	Employee findById(Long id);

	void saveEmployee(Employee emp);

	void updateEmployee(Employee emp);

	void deleteemployeeById(Long id);

	Iterable<Employee> findAllEmployees();

	void deleteAllEmployees();

	boolean isEmployeeExist(Long id);

}
