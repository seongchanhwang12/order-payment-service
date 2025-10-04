package dev.chan.orderpaymentservice.domain.order;

import dev.chan.orderpaymentservice.common.Ensure;
import dev.chan.orderpaymentservice.common.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order {
    private Long id;
    private Long orderedBy;
    private LocalDateTime orderedAt;
    private Money totalAmount;

    private final List<OrderProduct> orderProducts = new ArrayList<>();

    public static Order create(Long memberId) {
        Order order = new Order();
        order.orderedBy = Ensure.nonNull(memberId, "Order.memberId");
        return order;
    }

    private Money calculateTotalAmount(){
        return orderProducts.stream()
                .map(OrderProduct::getProductTotalPriceAtOrder)
                .reduce(Money.ZERO, Money::add);
    }

    public void addOrderProduct(OrderProduct... orderProduct) {
        List<OrderProduct> orderProducts = List.of(orderProduct);

        if(orderProducts.isEmpty()) {
            throw new OrderProductRequiredException();
        }

        this.orderProducts.addAll(orderProducts);
        this.totalAmount = calculateTotalAmount();
    }
}
