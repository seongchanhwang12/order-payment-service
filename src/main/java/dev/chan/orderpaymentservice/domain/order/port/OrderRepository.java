package dev.chan.orderpaymentservice.domain.order.port;

import dev.chan.orderpaymentservice.domain.order.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    Optional<Order> findById(Long orderId);

    List<Order> findAll();

    long count();

}