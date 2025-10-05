package dev.chan.orderpaymentservice.domain.product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long productId);
    void save(Product product);
}
