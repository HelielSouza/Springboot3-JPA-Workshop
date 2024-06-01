package com.springbackend.webservice.dto;

import com.springbackend.webservice.entities.enums.RoleName;

public record CreateUserDto(

		String name,
        String email,
        String password,
        String phone,
        RoleName role
) {
}