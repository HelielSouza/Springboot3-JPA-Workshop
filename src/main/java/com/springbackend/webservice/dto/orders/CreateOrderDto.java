package com.springbackend.webservice.dto.orders;

import java.util.List;

public record CreateOrderDto(String orderStatus, Long clientId, List<CreateOrderItemDto> items) {}