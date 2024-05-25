package com.security.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/admin/home")
	public String getAdminpage() {
		return "admin";
	}
	
	@GetMapping("/user/home")
	public String getUserpage() {
		return "user";
	}
	
	@GetMapping("/home")
	public String getHomepage() {
		return "home";
	}
}
