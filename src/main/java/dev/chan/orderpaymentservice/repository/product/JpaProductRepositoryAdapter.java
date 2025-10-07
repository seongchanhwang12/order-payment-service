package dev.chan.orderpaymentservice.repository.product;

import dev.chan.orderpaymentservice.domain.product.Product;
import dev.chan.orderpaymentservice.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaProductRepositoryAdapter implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    @Override
    public Optional<Product> findById(Long productId) {
        return jpaProductRepository.findById(productId);
    }

    @Override
    public void save(Product product) {
        jpaProductRepository.save(product);
    }
}
