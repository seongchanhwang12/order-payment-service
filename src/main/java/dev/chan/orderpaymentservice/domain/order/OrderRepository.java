package dev.chan.orderpaymentservice.domain.order;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    Optional<Order> findById(Long orderId);

}