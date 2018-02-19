package com.dbi.shyam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@RequestMapping("/test/welcome")
	public String welcome() {
		return "Welcome Shyam Ji!";
	}
}