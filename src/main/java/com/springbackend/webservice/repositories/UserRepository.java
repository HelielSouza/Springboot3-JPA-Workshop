package com.springbackend.webservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springbackend.webservice.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
