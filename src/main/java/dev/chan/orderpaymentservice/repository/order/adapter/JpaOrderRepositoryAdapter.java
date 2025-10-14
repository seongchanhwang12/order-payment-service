package dev.chan.orderpaymentservice.repository.order.adapter;

import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.domain.order.port.OrderRepository;
import dev.chan.orderpaymentservice.repository.order.JpaOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public long count() {
        return orderRepository.count();
    }


}
