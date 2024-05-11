package com.springbackend.webservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbackend.webservice.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
