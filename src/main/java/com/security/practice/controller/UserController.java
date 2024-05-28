package com.security.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.practice.WebToken.JwtService;
import com.security.practice.model.LoginForm;

import com.security.practice.service.NewUserService;

@RestController
public class UserController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private NewUserService newUserService;

	@GetMapping("/admin/home")
	public String getAdminpage() {
		return "Welcome, You are Admin User.";
	}

	@GetMapping("/user/home")
	public String getUserpage() {
		return "Welcome, You are Normal User.";
	}

	@GetMapping("/home")
	public String getHomepage() {
		return "Welcome, You are Home User.";
	}

	@PostMapping("/authenticate")
	public String authenticatedAndGetToken(@RequestBody LoginForm loginForm) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(newUserService.loadUserByUsername(loginForm.username()));
		} else {
			throw new UsernameNotFoundException("Invalid Creadentials.");
		}
	}
}
