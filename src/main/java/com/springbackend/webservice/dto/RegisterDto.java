package com.springbackend.webservice.dto;

import com.springbackend.webservice.entities.enums.UserRole;

import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String email,@NotNull String password, @NotNull UserRole role ) { 
    
}
