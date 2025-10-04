package dev.chan.orderpaymentservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class Product {
    private final long id;
    private final String name;
    private final Money price;
    private final String description;
    private final Long sellerId;
    private int stockQuantity;
    private LocalDateTime registeredAt;

    @Builder
    public Product(long id, String name, Money price, String description, Long sellerId, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.sellerId = sellerId;
        this.stockQuantity = stockQuantity;
    }

    public void decreaseStock(int orderQuantity) {
        if(orderQuantity > stockQuantity){
            throw new IllegalArgumentException("Order quantity exceeds stock limit");
        }
        stockQuantity -= orderQuantity;
    }
}
