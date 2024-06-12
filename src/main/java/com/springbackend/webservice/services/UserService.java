package com.springbackend.webservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.springbackend.webservice.entities.User;
import com.springbackend.webservice.repositories.UserRepository;
import com.springbackend.webservice.services.exceptions.DatabaseException;
import com.springbackend.webservice.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;   
	
	@Autowired
	private PasswordService passwordService;
   
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findByID(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User obj) {
		String encodedPassword = passwordService.encodePassword(obj.getPassword());
		
		if (obj.getAccountNonExpired() == null) {
            obj.setAccountNonExpired(true);
        }
        if (obj.getAccountNonLocked() == null) {
            obj.setAccountNonLocked(true);
        }
        if (obj.getCredentialsNonExpired() == null) {
            obj.setCredentialsNonExpired(true);
        }
        if (obj.getEnabled() == null) {
            obj.setEnabled(true);
        }
	        
		obj.setPassword(encodedPassword);
		return repository.save(obj);
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
		entity.setUserName(obj.getUserName());
		entity.setFullName(obj.getFullName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}
