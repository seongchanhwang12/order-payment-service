package dev.chan.orderpaymentservice.repository.product;

import dev.chan.orderpaymentservice.domain.ProductMother;
import dev.chan.orderpaymentservice.domain.product.Product;
import dev.chan.orderpaymentservice.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import({JpaProductRepositoryAdapter.class})
class JpaProductRepositoryAdapterTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void 상품을_저장하고_조회할수_있다() {
        //given
        Product product = ProductMother.any();

        //when
        productRepository.save(product);
        Optional<Product> optProduct = productRepository.findById(product.getId());
        Product found = optProduct.orElse(null);

        //then
        assertThat(optProduct).isPresent();
        assertThat(product.getName()).isEqualTo(found.getName());
        
    }



}