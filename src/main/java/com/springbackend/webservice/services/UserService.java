package com.springbackend.webservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.springbackend.webservice.dto.CreateUserDto;
import com.springbackend.webservice.dto.LoginUserDto;
import com.springbackend.webservice.dto.RecoveryJwtTokenDto;
import com.springbackend.webservice.entities.Role;
import com.springbackend.webservice.entities.User;
import com.springbackend.webservice.entities.enums.RoleName;
import com.springbackend.webservice.entities.implementations.UserDetailsImpl;
import com.springbackend.webservice.repositories.RoleRepository;
import com.springbackend.webservice.repositories.UserRepository;
import com.springbackend.webservice.security.SecurityConfiguration;
import com.springbackend.webservice.services.exceptions.DatabaseException;
import com.springbackend.webservice.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SecurityConfiguration securityConfiguration;
    
    @Autowired
    private RoleRepository roleRepository;
    
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findByID(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
  public void createUser(CreateUserDto createUserDto) {
        List<Role> roles = getOrCreateRoles(createUserDto.roles());
         
        User newUser = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .phone(createUserDto.phone())
                .roles(roles)
                .build();

        repository.save(newUser);
    }

	
	public void delete(Long id) {
		try {
			repository.deleteById(id);	
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public User update(Long id, User obj) {
		try {
			User entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
	
	private List<Role> getOrCreateRoles(List<RoleName> roleNames) {
        List<Role> roles = new ArrayList<>();

        for (RoleName roleName : roleNames) {
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                role = Role.builder().name(roleName).build();
                roleRepository.save(role); 
            }
            roles.add(role);
        }

        return roles;
    }
	
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }
}
