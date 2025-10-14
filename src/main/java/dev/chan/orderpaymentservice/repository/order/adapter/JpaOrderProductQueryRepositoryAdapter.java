package dev.chan.orderpaymentservice.repository.order.adapter;

import dev.chan.orderpaymentservice.domain.order.port.OrderProductQueryRepository;
import dev.chan.orderpaymentservice.repository.order.JpaOrderProductQueryRepository;
import dev.chan.orderpaymentservice.repository.order.query.OrderProductProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaOrderProductQueryRepositoryAdapter implements OrderProductQueryRepository {

    private final JpaOrderProductQueryRepository orderProductQueryRepository;


    @Override
    public List<OrderProductProjection> findProductsByOrderId(long orderId) {
        return orderProductQueryRepository.findProductsByOrderId(orderId);
    }
}
