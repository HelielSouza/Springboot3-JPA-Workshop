package com.springbackend.webservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbackend.webservice.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
	
}