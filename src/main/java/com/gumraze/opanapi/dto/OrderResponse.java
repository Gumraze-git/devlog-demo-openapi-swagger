package com.gumraze.opanapi.dto;

import com.gumraze.opanapi.entity.OrderStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private LocalDateTime orderedAt;
    private OrderStatus status;
    private int totalPrice;
    private List<OrderItemResponse> items = new ArrayList<>();
}
