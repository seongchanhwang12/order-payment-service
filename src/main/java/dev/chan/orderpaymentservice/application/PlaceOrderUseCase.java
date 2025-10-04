package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.dto.OrderResult;
import dev.chan.orderpaymentservice.application.dto.OrderedProduct;
import dev.chan.orderpaymentservice.application.dto.PlaceOrderCommand;
import dev.chan.orderpaymentservice.domain.Order;
import dev.chan.orderpaymentservice.domain.OrderProduct;
import dev.chan.orderpaymentservice.domain.Product;

import dev.chan.orderpaymentservice.repository.OrderProductRepository;
import dev.chan.orderpaymentservice.repository.OrderRepository;
import dev.chan.orderpaymentservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class PlaceOrderUseCase {

    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;

    public OrderResult handle(PlaceOrderCommand cmd) {
        // 프로덕트 조회 -
        Product product = productRepository.findById(cmd.productId())
                .orElseThrow();

        // 주문 생성
        Order order = Order.create(cmd.memberId());
        orderRepository.save(order);

        // 주문 상품 생성
        OrderProduct orderProduct = OrderProduct.of(
                order,
                product.getId(),
                cmd.orderQuantity(),
                product.getPrice(),
                product.getName());

        product.decreaseStock(cmd.orderQuantity());

        orderProductRepository.save(orderProduct);

        order.addOrderProduct(orderProduct);

        OrderedProduct orderedProduct = new OrderedProduct(
                orderProduct.getProductId(),
                orderProduct.getProductName(),
                orderProduct.getProductPrice(),
                orderProduct.getOrderQuantity());

        return new OrderResult(
                order.getId(),
                order.getOrderedBy(),
                order.getOrderedAt(),
                order.getTotalAmount(),
                List.of(orderedProduct)
        );
    }
}
