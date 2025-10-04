package dev.chan.orderpaymentservice.domain.product;

import dev.chan.orderpaymentservice.common.Ensure;
import dev.chan.orderpaymentservice.common.Money;
import dev.chan.orderpaymentservice.common.Quantity;
import dev.chan.orderpaymentservice.domain.InsufficientStockException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Product {
    private long id;
    private String name;
    private Money price;
    private Long sellerId;
    private Quantity stockQuantity;
    private LocalDateTime registeredAt;
    private String description;


    @Builder
    public Product(String name, Money price, String description, Long sellerId, Quantity stockQuantity) {
        this.name = Ensure.nonBlank(name, "Product.name");
        this.price = Ensure.nonNull(price, "Product.price");
        this.sellerId = Ensure.nonNull(sellerId, "Product.sellerId");
        this.stockQuantity = Ensure.nonNull(stockQuantity, "Product.stockQuantity");
        this.description = description;

    }

    public void decreaseStock(Quantity orderQuantity) {
        if(stockQuantity.isLessThan(orderQuantity))
            throw new InsufficientStockException(orderQuantity.value());
        stockQuantity = stockQuantity.subtract(orderQuantity);

    }
}
