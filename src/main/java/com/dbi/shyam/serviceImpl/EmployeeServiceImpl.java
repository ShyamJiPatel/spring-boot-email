package com.dbi.shyam.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbi.shyam.config.CustomUserDetails;
import com.dbi.shyam.entities.Employee;
import com.dbi.shyam.repositories.EmployeeRepository;
import com.dbi.shyam.services.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Transactional
	public Employee findById(Long id) {
		return employeeRepository.findOne(id);
	}

	@Transactional
	public void saveEmployee(Employee emp) {
		Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getId();
		emp.setUserId(userId);
		employeeRepository.save(emp);
	}

	@Transactional
	public void updateEmployee(Employee emp) {
		employeeRepository.save(emp);
	}

	@Transactional
	public void deleteemployeeById(Long id) {
		employeeRepository.delete(id);
	}

	@Transactional
	public Iterable<Employee> findAllEmployees() {
		Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getId();
		return employeeRepository.findAllByUserId(userId);
	}

	@Transactional
	public void deleteAllEmployees() {
		employeeRepository.deleteAll();
	}

	@Transactional
	public boolean isEmployeeExist(Long id) {
		return employeeRepository.exists(id);
	}
}
