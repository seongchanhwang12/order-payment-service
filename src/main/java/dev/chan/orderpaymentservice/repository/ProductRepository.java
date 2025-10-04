package dev.chan.orderpaymentservice.repository;

import dev.chan.orderpaymentservice.domain.product.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long productId);
    void save(Product product);
}
