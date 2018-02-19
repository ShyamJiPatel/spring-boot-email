package com.dbi.shyam.services;

import com.dbi.shyam.entities.Role;

public interface RoleService {
	Role findByName(String name);
}
