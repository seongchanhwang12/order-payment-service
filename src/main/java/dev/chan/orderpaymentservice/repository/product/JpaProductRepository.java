package dev.chan.orderpaymentservice.repository.product;

import dev.chan.orderpaymentservice.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long> {

}
