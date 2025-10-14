package dev.chan.orderpaymentservice.repository.order;

import dev.chan.orderpaymentservice.domain.order.OrderMother;
import dev.chan.orderpaymentservice.domain.order.OrderProductMother;
import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.domain.order.entity.OrderProduct;
import dev.chan.orderpaymentservice.domain.order.port.OrderProductRepository;
import dev.chan.orderpaymentservice.repository.order.adapter.JpaOrderProductRepositoryAdapter;
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
    void 주문상품을_저장하면_정상적으로_조회할_수_있다(){
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