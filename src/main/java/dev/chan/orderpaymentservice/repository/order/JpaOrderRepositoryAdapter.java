package dev.chan.orderpaymentservice.repository.order;

import dev.chan.orderpaymentservice.domain.order.Order;
import dev.chan.orderpaymentservice.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaOrderRepositoryAdapter implements OrderRepository {

    private final JpaOrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        Optional<Order> byId = orderRepository.findById(orderId);
        return byId;
    }

}
