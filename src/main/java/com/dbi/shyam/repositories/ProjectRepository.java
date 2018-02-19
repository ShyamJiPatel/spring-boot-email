package com.dbi.shyam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbi.shyam.entities.Project;

@Repository("projectRepository")
public interface ProjectRepository extends JpaRepository<Project, Long> {
	List<Project> findAllByUserId(Long userId);
}
