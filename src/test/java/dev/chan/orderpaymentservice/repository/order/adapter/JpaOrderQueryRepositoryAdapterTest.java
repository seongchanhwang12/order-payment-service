package dev.chan.orderpaymentservice.repository.order.adapter;

import dev.chan.orderpaymentservice.application.order.dto.OrderDetail;
import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.domain.order.OrderMother;
import dev.chan.orderpaymentservice.domain.order.port.OrderQueryRepository;
import dev.chan.orderpaymentservice.domain.order.port.OrderRepository;
import dev.chan.orderpaymentservice.repository.order.query.OrderProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({JpaOrderQueryRepositoryAdapter.class, JpaOrderRepositoryAdapter.class})
class JpaOrderQueryRepositoryAdapterTest {

    @Autowired
    OrderQueryRepository sut;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void 주문데이터가_존재하면_조회한다() {
        //given
        Order order = OrderMother.any();

        //when
        orderRepository.save(order);
        Optional<OrderDetail> orderDetail = sut.findDetailById(order.getId());

        //then
        assertThat(orderDetail).isPresent();
    }




}