package com.springbackend.webservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbackend.webservice.entities.Order;
import com.springbackend.webservice.entities.User;
import com.springbackend.webservice.repositories.OrderRepository;
import com.springbackend.webservice.repositories.UserRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	public List<Order> findAll() {
		return repository.findAll();
	}
	
	public Order findByID(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.get();
	}
}
