package com.gumraze.opanapi.service;

import com.gumraze.opanapi.dto.OrderCreateRequest;
import com.gumraze.opanapi.dto.OrderItemRequest;
import com.gumraze.opanapi.dto.OrderItemResponse;
import com.gumraze.opanapi.dto.OrderResponse;
import com.gumraze.opanapi.dto.OrderUpdateRequest;
import com.gumraze.opanapi.entity.Coffee;
import com.gumraze.opanapi.entity.Order;
import com.gumraze.opanapi.entity.OrderItem;
import com.gumraze.opanapi.repository.CoffeeRepository;
import com.gumraze.opanapi.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CoffeeRepository coffeeRepository;

    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {
        Order order = Order.builder().build();
        applyItems(order, request.getItems());
        order.setTotalPrice(calculateTotal(order.getItems()));
        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findWithItemsById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        return toResponse(order);
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        Order order = orderRepository.findWithItemsById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        clearItems(order);
        applyItems(order, request.getItems());
        order.setTotalPrice(calculateTotal(order.getItems()));
        return toResponse(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

    private void applyItems(Order order, List<OrderItemRequest> items) {
        for (OrderItemRequest itemRequest : items) {
            Coffee coffee = getCoffee(itemRequest.getCoffeeId());
            OrderItem item = OrderItem.builder()
                    .order(order)
                    .coffee(coffee)
                    .coffeeName(itemRequest.getCoffeeName())
                    .unitPrice(itemRequest.getUnitPrice())
                    .quantity(itemRequest.getQuantity())
                    .build();
            order.addItem(item);
        }
    }

    private Coffee getCoffee(Long coffeeId) {
        return coffeeRepository.findById(coffeeId)
                .orElseThrow(() -> new EntityNotFoundException("Coffee not found: " + coffeeId));
    }

    private void clearItems(Order order) {
        order.getItems().forEach(item -> item.setOrder(null));
        order.getItems().clear();
    }

    private int calculateTotal(List<OrderItem> items) {
        return items.stream()
                .mapToInt(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getCoffee() == null ? null : item.getCoffee().getId(),
                        item.getCoffeeName(),
                        item.getUnitPrice(),
                        item.getQuantity(),
                        item.getUnitPrice() * item.getQuantity()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getOrderedAt(),
                order.getStatus(),
                order.getTotalPrice(),
                itemResponses
        );
    }
}
