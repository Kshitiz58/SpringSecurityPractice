package com.security.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.security.practice.model.NewUser;
import com.security.practice.repository.NewUserRepository;

@Controller
public class UserController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private NewUserRepository newUserRepo;

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

	@GetMapping("/signup")
	public String getSignupPage(){
		return "signup";
	}
	
	@GetMapping({"/","/login"})
	public String getLoginPage() {
		return "login";
	}
	
	@PostMapping("/signup")
	public String PostSignupPage(@ModelAttribute NewUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		newUserRepo.save(user);
		return "redirect:/login";
	}
}
