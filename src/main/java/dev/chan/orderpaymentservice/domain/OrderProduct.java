package dev.chan.orderpaymentservice.domain;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class OrderProduct {
    @ManyToOne
    private Order order;
    private String productName;
    private int orderQuantity;
    private long productId;
    private Money productPrice;
    private Money productTotalPriceAtOrder;

    public static OrderProduct of(Order order, long productId, int orderQuantity, Money productPrice, String productName) {
        Money productPriceAtOrder = productPrice.multiply(orderQuantity);
        return new OrderProduct(
                order,
                productName,
                orderQuantity,
                productId,
                productPrice,
                productPriceAtOrder);
    }
}
