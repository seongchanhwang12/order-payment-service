package dev.chan.orderpaymentservice.application.order;

import dev.chan.orderpaymentservice.application.order.dto.OrderResult;
import dev.chan.orderpaymentservice.application.order.dto.OrderedProduct;
import dev.chan.orderpaymentservice.application.order.dto.PlaceOrderCommand;
import dev.chan.orderpaymentservice.application.product.ProductNotFoundException;
import dev.chan.orderpaymentservice.domain.common.Quantity;

import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.domain.order.entity.OrderProduct;
import dev.chan.orderpaymentservice.domain.product.Product;
import dev.chan.orderpaymentservice.domain.order.port.OrderProductRepository;
import dev.chan.orderpaymentservice.domain.order.port.OrderRepository;
import dev.chan.orderpaymentservice.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceOrderUseCase {

    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResult handle(PlaceOrderCommand cmd) {
        Quantity orderQuantity = Quantity.of(cmd.orderQuantity());

        // 프로덕트 조회
        Product product = productRepository.findById(cmd.productId())
                .orElseThrow(()-> new ProductNotFoundException(cmd.productId()));

        // 재고 감산
        product.decreaseStock(orderQuantity);

        // 주문 생성
        Order order = Order.create(cmd.memberId());
        orderRepository.save(order);

        // 주문 상품 생성
        OrderProduct orderProduct = OrderProduct.of(
                order,
                product.getId(),
                orderQuantity,
                product.getPrice(),
                product.getName());

        // 주문 상품 저장
        orderProductRepository.save(orderProduct);

        // 주문에 주문 상품 내역 추가
        order.addOrderProduct(orderProduct);

        // 주문 상품 생성
        OrderedProduct orderedProduct = new OrderedProduct(
                orderProduct.getProductId(),
                orderProduct.getProductName(),
                orderProduct.getProductPrice(),
                orderProduct.getOrderQuantity().value());

        // 주문 결과 반환
        return new OrderResult(
                order.getId(),
                order.getOrderedBy(),
                order.getOrderedAt(),
                order.getTotalPrice(),
                List.of(orderedProduct)
        );
    }
}
