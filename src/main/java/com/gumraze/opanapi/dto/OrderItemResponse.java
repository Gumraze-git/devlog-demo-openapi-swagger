package com.gumraze.opanapi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;
    private Long coffeeId;
    private String coffeeName;
    private int unitPrice;
    private int quantity;
    private int lineTotal;
}
