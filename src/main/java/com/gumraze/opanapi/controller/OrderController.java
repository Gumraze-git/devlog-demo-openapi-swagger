package com.gumraze.opanapi.controller;

import com.gumraze.opanapi.dto.OrderCreateRequest;
import com.gumraze.opanapi.dto.OrderResponse;
import com.gumraze.opanapi.dto.OrderUpdateRequest;
import com.gumraze.opanapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "주문", description = "커피 주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 생성", description = "주문 항목을 포함해 새로운 주문을 생성합니다.")
    @RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = OrderCreateRequest.class),
                    examples = @ExampleObject(
                            name = "주문 생성 예시",
                            value = """
                                    {
                                      "items": [
                                        {
                                          "coffeeId": 1,
                                          "coffeeName": "아메리카노",
                                          "unitPrice": 3500,
                                          "quantity": 2
                                        },
                                        {
                                          "coffeeId": 2,
                                          "coffeeName": "라떼",
                                          "unitPrice": 4200,
                                          "quantity": 1
                                        }
                                      ]
                                    }
                                    """
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성됨",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 검증 실패", content = @Content)
    })
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderCreateRequest request
    ) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 단건 조회", description = "주문 ID로 주문 상세를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음", content = @Content)
    })
    public ResponseEntity<OrderResponse> getOrder(
            @Parameter(description = "주문 ID", example = "1")
            @PathVariable Long orderId
    ) {
        OrderResponse response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "주문 수정(전체 교체)", description = "주문 항목을 전부 교체합니다.")
    @RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = OrderUpdateRequest.class),
                    examples = @ExampleObject(
                            name = "주문 수정 예시",
                            value = """
                                    {
                                      "items": [
                                        {
                                          "coffeeId": 3,
                                          "coffeeName": "바닐라 라떼",
                                          "unitPrice": 4800,
                                          "quantity": 1
                                        }
                                      ]
                                    }
                                    """
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 검증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음", content = @Content)
    })
    public ResponseEntity<OrderResponse> updateOrder(
            @Parameter(description = "주문 ID", example = "1")
            @PathVariable Long orderId,
            @Valid @RequestBody OrderUpdateRequest request
    ) {
        OrderResponse response = orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 삭제", description = "주문 ID로 주문을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제됨"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음", content = @Content)
    })
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "주문 ID", example = "1")
            @PathVariable Long orderId
    ) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
