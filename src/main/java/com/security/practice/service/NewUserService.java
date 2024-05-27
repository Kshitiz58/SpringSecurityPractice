package com.security.practice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.practice.model.NewUser;
import com.security.practice.repository.NewUserRepository;

@Service
public class NewUserService implements UserDetailsService{

	@Autowired
	private NewUserRepository newUserRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		NewUser user = newUserRepo.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found: "+username));
		
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(getRoles(user))
				.build();
	}
	private String [] getRoles(NewUser user) {
		if(user.getRole() == null || user.getRole().isEmpty()) {
			return new String [] {"USER"};
		}
		return user.getRole().split(",");
	}

}
