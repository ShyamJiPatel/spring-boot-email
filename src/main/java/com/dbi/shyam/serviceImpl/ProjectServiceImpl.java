package com.dbi.shyam.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dbi.shyam.config.CustomUserDetails;
import com.dbi.shyam.entities.Project;
import com.dbi.shyam.repositories.ProjectRepository;
import com.dbi.shyam.services.ProjectService;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project findById(Long id) {
		return projectRepository.findOne(id);
	}

	public void saveProject(Project project) {
		Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getId();
		project.setUserId(userId);
		projectRepository.save(project);
	}

	public void updateProject(Project project) {
		Project oldProjet = projectRepository.findOne(project.getId());

		oldProjet.setName(project.getName());
		oldProjet.setDescription(project.getDescription());
		oldProjet.setEmployees(project.getEmployees());

		projectRepository.saveAndFlush(oldProjet);
	}

	public void deleteProjectById(Long id) {
		projectRepository.delete(id);
	}

	public Iterable<Project> findAllProjects() {
		Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getId();
		return projectRepository.findAllByUserId(userId);
	}

	public void deleteAllProjects() {
		projectRepository.deleteAll();
	}

	public boolean isProjectExist(Long id) {
		return projectRepository.exists(id);
	}
}
