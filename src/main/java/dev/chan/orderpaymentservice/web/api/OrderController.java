package dev.chan.orderpaymentservice.web.api;

import dev.chan.orderpaymentservice.application.order.GetOrderUseCase;
import dev.chan.orderpaymentservice.application.order.PlaceOrderUseCase;
import dev.chan.orderpaymentservice.application.order.dto.OrderDetail;
import dev.chan.orderpaymentservice.application.order.dto.OrderResult;
import dev.chan.orderpaymentservice.application.order.dto.PlaceOrderCommand;
import dev.chan.orderpaymentservice.common.ApiResponse;
import dev.chan.orderpaymentservice.repository.order.JpaOrderProductQueryRepository;
import dev.chan.orderpaymentservice.web.dto.PlaceOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;

    /**
     * 주문 생성
     * 주문 생성을 application 레이어에 요청하고, 완료된 결과를 리턴받아 응답합니다.
     * @param req
     * @return ApiResponse - 공통 API 응답
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResult>> placeOrder(@Valid @RequestBody PlaceOrderRequest req) {
        OrderResult result = placeOrderUseCase.handle(PlaceOrderCommand.of(
                req.getMemberId(),
                req.getProductId(),
                req.getOrderQuantity())
        );
        return ResponseEntity.ok(ApiResponse.success(result));

    }

    /**
     * 주문 상세 조회 컨트롤러
     * 특정 주문의 세부사항을 조회한다.
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetail>> findAll(@PathVariable long orderId) {
        OrderDetail handle = getOrderUseCase.handle(orderId);
        return ResponseEntity.ok(ApiResponse.success(handle));
    }


}
