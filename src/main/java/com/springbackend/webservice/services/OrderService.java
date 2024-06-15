package com.springbackend.webservice.services;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbackend.webservice.dto.orders.CreateOrderDto;
import com.springbackend.webservice.dto.orders.CreateOrderItemDto;
import com.springbackend.webservice.entities.Order;
import com.springbackend.webservice.entities.OrderItem;
import com.springbackend.webservice.entities.Product;
import com.springbackend.webservice.entities.User;
import com.springbackend.webservice.entities.enums.OrderStatus;
import com.springbackend.webservice.repositories.OrderRepository;
import com.springbackend.webservice.repositories.ProductRepository;
import com.springbackend.webservice.repositories.UserRepository;
import com.springbackend.webservice.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private ProductRepository productRepository;
	
	 @Autowired
	 private OrderRepository orderRepository;
	
	
	public List<Order> findAll() {
		return repository.findAll();
	}
	
	public Order findByID(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id)); 
	}
	
	public Order insert(CreateOrderDto dto) {
		Order order = fromDto(dto);
        return orderRepository.save(order);
	}
		
	private Order fromDto(CreateOrderDto dto) {
        User client = userRepository.findById(dto.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        Instant now = Instant.now().atOffset(ZoneOffset.ofHours(-3)).toInstant();
        
        OrderStatus orderStatus = OrderStatus.valueOf(dto.orderStatus().toUpperCase());
        Order order = new Order(null, now, client, orderStatus);
        
        for (CreateOrderItemDto itemDto : dto.items()) {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            OrderItem orderItem = new OrderItem(order, product, itemDto.quantity(), product.getPrice());
            order.getItems().add(orderItem);
        }
        return order;
    }
}
