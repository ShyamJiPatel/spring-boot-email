package com.dbi.shyam.app;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dbi.shyam.config.CustomUserDetails;
import com.dbi.shyam.entities.Role;
import com.dbi.shyam.entities.User;
import com.dbi.shyam.repositories.UserRepository;
import com.dbi.shyam.services.UserService;

@SpringBootApplication
@EntityScan("com.dbi.shyam.entities")
@EnableJpaRepositories("com.dbi.shyam.repositories")
@ComponentScan("com.dbi.shyam")
public class MainApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	/**
	 * Password grants are switched on by injecting an AuthenticationManager.
	 * Here, we setup the builder so that the userDetailsService is the one we
	 * coded.
	 * 
	 * @param builder
	 * @param repository
	 * @throws Exception
	 */
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repository,
			UserService service) throws Exception {
		// Setup a default user if db is empty
		if (repository.count() == 0)
			service.saveUser(new User("test", "", "user@gmail.com", "User@1234", "", "",
					Arrays.asList(new Role("USER"), new Role("ACTUATOR"))));
		builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
	}

	/**
	 * We return an istance of our CustomUserDetails.
	 * 
	 * @param repository
	 * @return
	 */
	private UserDetailsService userDetailsService(final UserRepository repository) {
		return username -> new CustomUserDetails(repository.findByEmail(username));
	}

}
