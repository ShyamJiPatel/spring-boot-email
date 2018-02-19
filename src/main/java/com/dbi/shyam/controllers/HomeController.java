package com.dbi.shyam.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping(value = "/")
	public String index() {
		return "Hello world";
	}

	@GetMapping(value = "/ems")
	public String privateArea() {
		return "Private area";
	}

}
