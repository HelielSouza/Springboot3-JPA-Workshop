package com.springbackend.webservice.dto;

import java.util.List;

import com.springbackend.webservice.entities.Role;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles

) {
}
