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

import com.dbi.shyam.entities.Project;
import com.dbi.shyam.services.ProjectService;

@CrossOrigin(origins = { "http://192.168.170.112:4200", "http://localhost:4200" })
@RestController
public class ProjectRestController {

	private final String BASE_URL = "/project";

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = BASE_URL, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> listAllProjects() {
		ArrayList<Project> projects = (ArrayList<Project>) projectService.findAllProjects();
		if (projects == null) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Object>(projects, HttpStatus.OK);
	}

	@RequestMapping(value = BASE_URL
			+ "/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Project> getProject(@PathVariable("id") long id) {
		Project project = projectService.findById(id);
		if (project == null) {
			return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@RequestMapping(value = BASE_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createProject(@RequestBody Project project) {
		// System.out.println(map);
		projectService.saveProject(project);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = BASE_URL, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateProject(@RequestBody Project project) {
		projectService.updateProject(project);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = BASE_URL
			+ "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteProject(@PathVariable("id") long id) {
		projectService.deleteProjectById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = BASE_URL, method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAllProjects() {
		projectService.deleteAllProjects();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}