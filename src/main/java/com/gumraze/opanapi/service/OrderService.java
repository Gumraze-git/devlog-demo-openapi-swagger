package com.gumraze.opanapi.service;

import com.gumraze.opanapi.dto.OrderCreateRequest;
import com.gumraze.opanapi.dto.OrderResponse;
import com.gumraze.opanapi.dto.OrderUpdateRequest;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request);

    OrderResponse getOrder(Long orderId);

    List<OrderResponse> getOrders();

    OrderResponse updateOrder(Long orderId, OrderUpdateRequest request);

    void deleteOrder(Long orderId);
}
