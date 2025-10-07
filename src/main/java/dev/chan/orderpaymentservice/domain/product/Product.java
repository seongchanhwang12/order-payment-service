package dev.chan.orderpaymentservice.domain.product;

import dev.chan.orderpaymentservice.common.Ensure;
import dev.chan.orderpaymentservice.domain.common.Money;
import dev.chan.orderpaymentservice.domain.common.Quantity;
import dev.chan.orderpaymentservice.domain.InsufficientStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue
    private long id;
    private String name;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price"))
    private Money price;
    private Long sellerId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity stockQuantity;

    @CurrentTimestamp
    private LocalDateTime registeredAt;

    private String description;

    @Builder(access = AccessLevel.PROTECTED)
    public Product(String name, Money price, String description, Long sellerId, Quantity stockQuantity) {
        this.name = Ensure.nonBlank(name, "Product.name");
        this.price = Ensure.nonNull(price, "Product.price");
        this.sellerId = Ensure.nonNull(sellerId, "Product.sellerId");
        this.stockQuantity = Ensure.nonNull(stockQuantity, "Product.stockQuantity");
        this.description = description;
    }

    public static Product of(String name, Money price, String description, Long sellerId, Quantity stockQuantity) {
        return Product.builder()
                .name(name)
                .price(price)
                .description(description)
                .sellerId(sellerId)
                .stockQuantity(stockQuantity)
                .build();
    }

    public void decreaseStock(Quantity orderQuantity) {
        if(stockQuantity.isLessThan(orderQuantity))
            throw new InsufficientStockException(orderQuantity.value());
        stockQuantity = stockQuantity.subtract(orderQuantity);

    }
}
