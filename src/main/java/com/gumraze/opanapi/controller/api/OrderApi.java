package com.gumraze.opanapi.controller.api;

import com.gumraze.opanapi.dto.OrderCreateRequest;
import com.gumraze.opanapi.dto.OrderResponse;
import com.gumraze.opanapi.dto.OrderUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "주문", description = "커피 주문 관련 API")
@CommonErrorResponses
public interface OrderApi {

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
                    content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    })
    ResponseEntity<OrderResponse> createOrder(OrderCreateRequest request);

    @Operation(summary = "주문 단건 조회", description = "주문 ID로 주문 상세를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    })
    ResponseEntity<OrderResponse> getOrder(
            @Parameter(description = "주문 ID", example = "1")
            Long orderId
    );

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
                    content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    })
    ResponseEntity<OrderResponse> updateOrder(
            @Parameter(description = "주문 ID", example = "1")
            Long orderId,
            OrderUpdateRequest request
    );

    @Operation(summary = "주문 삭제", description = "주문 ID로 주문을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제됨")
    })
    ResponseEntity<Void> deleteOrder(
            @Parameter(description = "주문 ID", example = "1")
            Long orderId
    );
}
