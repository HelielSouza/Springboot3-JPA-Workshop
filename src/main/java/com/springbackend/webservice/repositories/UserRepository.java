package com.springbackend.webservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.springbackend.webservice.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 UserDetails findByEmail(String email);
}
