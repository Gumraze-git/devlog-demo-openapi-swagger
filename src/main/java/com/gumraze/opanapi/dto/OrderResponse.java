package com.gumraze.opanapi.dto;

import com.gumraze.opanapi.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private LocalDateTime orderedAt;
    private OrderStatus status;
    private int totalPrice;
    private List<OrderItemResponse> items = new ArrayList<>();
}
