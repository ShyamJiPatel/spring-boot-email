package com.dbi.shyam.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbi.shyam.email.EmailService;
import com.dbi.shyam.entities.Role;
import com.dbi.shyam.entities.User;
import com.dbi.shyam.repositories.UserRepository;
import com.dbi.shyam.services.RoleService;
import com.dbi.shyam.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private EmailService emailService;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Transactional
	public void saveUser(User user) {
		user.setPassword(getPasswordEncoder().encode(user.getPassword()));
		List<Role> roles = user.getRoles();
		if (roles == null || roles.size() == 0) {
			Role role = roleService.findByName("USER");
			if (role == null) {
				user.setRoles(Arrays.asList(new Role("USER")));
			} else {
				user.setRoles(Arrays.asList(role));
			}
		}
		userRepository.save(user);
//		emailService.send(user, true);
		emailService.send(user, false);
	}

	@Transactional
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Transactional
	public void deleteUserById(Long id) {
		userRepository.delete(id);
	}

	public Iterable<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public void deleteAllUsers() {
		userRepository.deleteAll();
	}

	@Transactional
	public boolean isUserExist(Long id) {
		return userRepository.exists(id);
	}

	@Override
	public User login(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

}
