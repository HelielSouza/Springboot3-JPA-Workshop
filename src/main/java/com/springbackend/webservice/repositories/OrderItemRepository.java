package com.springbackend.webservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbackend.webservice.entities.OrderItem;
import com.springbackend.webservice.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
