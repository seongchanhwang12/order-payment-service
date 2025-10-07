package dev.chan.orderpaymentservice.domain.order;

import dev.chan.orderpaymentservice.common.Ensure;
import dev.chan.orderpaymentservice.domain.common.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long orderedBy;

    @CurrentTimestamp
    private LocalDateTime orderedAt;

    @AttributeOverride(name="amount", column = @Column(name = "total_price") )
    private Money totalPrice;

    @Transient
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
        this.totalPrice = calculateTotalAmount();
    }
}
