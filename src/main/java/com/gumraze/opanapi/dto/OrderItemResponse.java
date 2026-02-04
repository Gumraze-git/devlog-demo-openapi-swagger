package com.gumraze.opanapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Long coffeeId;
    private String coffeeName;
    private int unitPrice;
    private int quantity;
    private int lineTotal;
}
