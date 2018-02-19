package com.dbi.shyam.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dbi.shyam.entities.Employee;
import com.dbi.shyam.services.EmployeeService;

@CrossOrigin(origins = { "http://192.168.170.112:4200", "http://localhost:4200" })
@RestController
public class EmployeeRestController {

	private final String BASE_URL = "/employee";

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = BASE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Employee>> listAllUsers() {
		ArrayList<Employee> employees = (ArrayList<Employee>) employeeService.findAllEmployees();
		if (employees == null) {
			return new ResponseEntity<ArrayList<Employee>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<Employee>>(employees, HttpStatus.OK);

	}

	@RequestMapping(value = BASE_URL + "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") long id) {
		Employee employee = employeeService.findById(id);
		if (employee == null) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@RequestMapping(value = BASE_URL, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createUser(@RequestBody Employee employee) {
		employeeService.saveEmployee(employee);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = BASE_URL, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> updateUser(@RequestBody Employee employee) {
		if (employee == null) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		employeeService.updateEmployee(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@RequestMapping(value = BASE_URL
			+ "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
		employeeService.deleteemployeeById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = BASE_URL, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> deleteAllUsers() {
		employeeService.deleteAllEmployees();
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	}

}