package dev.chan.orderpaymentservice.application.dto;

import dev.chan.orderpaymentservice.domain.product.Product;

import java.math.BigDecimal;

public record ProductView(Long id, String name, BigDecimal price, String description) {
    public static ProductView from(Product product) {
        return new ProductView(
                product.getId(),
                product.getName(),
                product.getPrice().amount(),
                product.getDescription());
    }
}
