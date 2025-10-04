package dev.chan.orderpaymentservice.repository;

import dev.chan.orderpaymentservice.domain.Order;

public interface OrderRepository {
    void save(Order order);
}