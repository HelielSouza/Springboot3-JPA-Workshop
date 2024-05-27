package com.springbackend.webservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbackend.webservice.entities.Role;
import com.springbackend.webservice.entities.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
