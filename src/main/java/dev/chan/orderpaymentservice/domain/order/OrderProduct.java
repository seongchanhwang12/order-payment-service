package dev.chan.orderpaymentservice.domain.order;

import dev.chan.orderpaymentservice.common.Ensure;
import dev.chan.orderpaymentservice.common.Money;
import dev.chan.orderpaymentservice.common.Quantity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class OrderProduct {

    private Long id;
    private Order order;
    private String productName;
    private Quantity orderQuantity;
    private long productId;
    private Money productPrice;
    private Money productTotalPriceAtOrder;

    @Builder
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
