package com.dbi.shyam.services;

import com.dbi.shyam.entities.Project;

public interface ProjectService {

	Project findById(Long id);

	void saveProject(Project project);

	void updateProject(Project project);

	void deleteProjectById(Long id);

	Iterable<Project> findAllProjects();

	void deleteAllProjects();

	boolean isProjectExist(Long id);

}
