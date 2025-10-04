package dev.chan.orderpaymentservice.domain;

import dev.chan.orderpaymentservice.application.dto.OrderedProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order {
    private Long id;
    private Long orderedBy;
    private LocalDateTime orderedAt;
    private Money totalAmount;

    private List<OrderProduct> orderProducts = new ArrayList<>();

    public static Order create(Long memberId) {
        Order order = new Order();
        order.orderedBy = memberId;
        return order;
    }

    public void withId(Long id) {
        this.id = id;
        this.orderedAt = LocalDateTime.now();
    }

    private Money calculateTotalAmount(){
        return orderProducts.stream()
                .map(OrderProduct::getProductTotalPriceAtOrder)
                .reduce(Money.ZERO, Money::add);
    }

    public void addOrderProduct(OrderProduct... orderProduct) {
        List<OrderProduct> orderProducts = List.of(orderProduct);
        this.orderProducts.addAll(orderProducts);
        this.totalAmount = calculateTotalAmount();
    }
}
