package dev.chan.orderpaymentservice.repository.order;

import dev.chan.orderpaymentservice.domain.OrderMother;
import dev.chan.orderpaymentservice.domain.OrderProductMother;
import dev.chan.orderpaymentservice.domain.common.Money;
import dev.chan.orderpaymentservice.domain.common.Quantity;
import dev.chan.orderpaymentservice.domain.order.Order;
import dev.chan.orderpaymentservice.domain.order.OrderProduct;
import dev.chan.orderpaymentservice.domain.order.OrderProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaOrderProductRepositoryAdapter.class})
class JpaOrderProductRepositoryAdapterTest {

    @Autowired
    OrderProductRepository orderProductRepository;

    @Test
    void 주문상품을_저장하고_조회할수_있다(){
        // given
        Order order = OrderMother.any();
        OrderProduct orderProduct = OrderProductMother.withOrder(order);

        // when
        orderProductRepository.save(orderProduct);
        Optional<OrderProduct> foundOrderProduct = orderProductRepository.findById(orderProduct.getId());

        // then
        assertThat(foundOrderProduct).isPresent();
        assertThat(foundOrderProduct.get().getOrder()).isEqualTo(order);
        assertThat(foundOrderProduct.get().getProductName()).isEqualTo("product");

    }


}