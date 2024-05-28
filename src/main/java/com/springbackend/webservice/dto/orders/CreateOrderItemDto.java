package com.springbackend.webservice.dto.orders;

public record CreateOrderItemDto(Long productId, Integer quantity) {}