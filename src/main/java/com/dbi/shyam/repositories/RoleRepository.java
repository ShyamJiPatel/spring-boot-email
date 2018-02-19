package com.dbi.shyam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbi.shyam.entities.Role;

/**
 * User repository for CRUD operations.
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
