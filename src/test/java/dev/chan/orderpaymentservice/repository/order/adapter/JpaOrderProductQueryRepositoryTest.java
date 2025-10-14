package dev.chan.orderpaymentservice.repository.order.adapter;

import dev.chan.orderpaymentservice.domain.order.OrderMother;
import dev.chan.orderpaymentservice.domain.order.OrderProductMother;
import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.domain.order.entity.OrderProduct;
import dev.chan.orderpaymentservice.domain.order.port.OrderProductQueryRepository;
import dev.chan.orderpaymentservice.domain.order.port.OrderProductRepository;
import dev.chan.orderpaymentservice.domain.order.port.OrderRepository;
import dev.chan.orderpaymentservice.repository.order.JpaOrderProductQueryRepository;
import dev.chan.orderpaymentservice.repository.order.query.OrderProductProjection;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import({JpaOrderProductQueryRepositoryAdapter.class,JpaOrderProductRepositoryAdapter.class, JpaOrderRepositoryAdapter.class})
class JpaOrderProductQueryRepositoryTest {

    @Autowired
    OrderProductQueryRepository sut;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void 저장된상품을_조회할수있다(){
        //given
        Order any = OrderMother.any();
        OrderProduct orderProduct = OrderProductMother.withOrder(any);

        orderRepository.save(any);
        orderProductRepository.save(orderProduct);

        //when
        List<OrderProductProjection> orderProductProjections = sut.findProductsByOrderId(any.getId());
        log.info(orderProductProjections.toString());

        //then
        assertThat(orderProductProjections.size()).isEqualTo(1);
        OrderProductProjection orderProductProjection = orderProductProjections.get(0);
        assertThat(orderProductProjection.getProductId()).isEqualTo(orderProduct.getProductId());
        assertThat(orderProductProjection.getProductName()).isEqualTo(orderProduct.getProductName());

    }

}