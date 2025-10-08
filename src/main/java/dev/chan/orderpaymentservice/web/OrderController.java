package dev.chan.orderpaymentservice.web;

import dev.chan.orderpaymentservice.application.PlaceOrderUseCase;
import dev.chan.orderpaymentservice.application.dto.OrderResult;
import dev.chan.orderpaymentservice.application.dto.PlaceOrderCommand;
import dev.chan.orderpaymentservice.common.ApiResponse;
import dev.chan.orderpaymentservice.web.dto.PlaceOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResult>> placeOrder(@RequestBody PlaceOrderRequest req) {
        OrderResult result = placeOrderUseCase.handle(PlaceOrderCommand.of(
                req.getMemberId(),
                req.getProductId(),
                req.getOrderQuantity())
        );

        return ResponseEntity.ok(ApiResponse.success(result));

    }
}
