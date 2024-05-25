package com.security.practice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.practice.model.NewUser;


public interface NewUserRepository extends JpaRepository<NewUser, Long>{

	Optional<NewUser> findByUsername(String username);
}
