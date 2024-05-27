package com.security.practice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;


//import com.security.practice.repository.NewUserRepository;
import com.security.practice.service.NewUserService;


@Configuration
@EnableWebSecurity
public class WebConfig {
	
	@Autowired
	private  NewUserService newUserService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request ->{
					request.requestMatchers("/css/**","/error/**").permitAll();
					request.requestMatchers("/home","/signup").permitAll();
					request.requestMatchers("/admin/**").hasRole("ADMIN");
					request.requestMatchers("/user/**").hasRole("USER");
					request.anyRequest().authenticated();
				})
//				.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
				.formLogin(httpSecurityFormLoginConfigurer ->{
					httpSecurityFormLoginConfigurer
					.loginPage("/login")
					.successHandler(new AuthSuccessHandler())
					.permitAll();
				})
				
				.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails User1 = User.builder()
//				.username("sulav")
//				.password(passwordEncoder().encode("123"))
//				.roles("ADMIN","USER")
//				.build();
//		
//		UserDetails User2 = User.builder()
//				.username("kshitiz")
//				.password(passwordEncoder().encode("123"))
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(User1,User2);
//	}
	@Bean
	public UserDetailsService userDetailsService() {
		return newUserService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(newUserService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

}
