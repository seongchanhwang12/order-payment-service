package dev.chan.orderpaymentservice.domain.order.entity;

import dev.chan.orderpaymentservice.common.Ensure;
import dev.chan.orderpaymentservice.domain.common.Money;
import dev.chan.orderpaymentservice.domain.common.Quantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ORDER_PRODUCT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long productId;

    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "order_quantity"))
    private Quantity orderQuantity;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "product_price"))
    private Money productPrice;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "product_total_price"))
    private Money productTotalPriceAtOrder;

    @Builder(access = AccessLevel.PROTECTED)
    public OrderProduct(
            Order order,
            Long productId,
            Quantity orderQuantity,
            Money productPrice,
            String productName,
            Money productTotalPriceAtOrder
    ){
        this.order = Ensure.nonNull(order, "OrderProduct.order");
        this.productName = Ensure.nonBlank(productName, "OrderProduct.productName");
        this.orderQuantity = Ensure.nonNull(orderQuantity, "OrderProduct.orderQuantity");
        this.productId = Ensure.nonNull(productId, "OrderProduct.productId");
        this.productPrice = Ensure.nonNull(productPrice, "OrderProduct.productPrice");
        this.productTotalPriceAtOrder = Ensure.nonNull(productTotalPriceAtOrder, "OrderProduct.productTotalPriceAtOrder");
    }

    public static OrderProduct of(
            Order order,
            Long productId,
            Quantity orderQuantity,
            Money productPrice,
            String productName
    ){
        Money productPriceAtOrder = productPrice.multiply(orderQuantity.value());
        return new OrderProduct(
                order,
                productId,
                orderQuantity,
                productPrice,
                productName,
                productPriceAtOrder);
    }
}
