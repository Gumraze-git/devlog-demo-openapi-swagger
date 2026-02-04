package com.gumraze.opanapi.service;

import com.gumraze.opanapi.dto.OrderCreateRequest;
import com.gumraze.opanapi.dto.OrderResponse;
import com.gumraze.opanapi.dto.OrderUpdateRequest;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request);

    OrderResponse getOrder(Long orderId);

    OrderResponse updateOrder(Long orderId, OrderUpdateRequest request);

    void deleteOrder(Long orderId);
}
