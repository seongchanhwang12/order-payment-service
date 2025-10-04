package dev.chan.orderpaymentservice.domain;

import dev.chan.orderpaymentservice.common.Money;
import dev.chan.orderpaymentservice.common.Quantity;
import dev.chan.orderpaymentservice.domain.product.Product;

public class ProductMother {

    public static Product withDefaultValues(){
        return Product.builder().name("Product")
                .stockQuantity(Quantity.of(10))
                .price(Money.of(10000))
                .description("Product description")
                .sellerId(1L)
                .build();
    }

    public static Product withStock(int stockQuantity) {
        return Product.builder()
                .name("Product")
                .stockQuantity(Quantity.of(stockQuantity))
                .price(Money.of(10000))
                .description("Product description")
                .sellerId(1L)
                .build();
    }
}
