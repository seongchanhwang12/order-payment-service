package dev.chan.orderpaymentservice.repository;

import dev.chan.orderpaymentservice.domain.order.Order;

public interface OrderRepository {
    void save(Order order);
}