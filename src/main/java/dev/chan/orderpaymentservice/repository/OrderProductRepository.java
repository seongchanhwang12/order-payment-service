package dev.chan.orderpaymentservice.repository;

import dev.chan.orderpaymentservice.domain.order.OrderProduct;

public interface OrderProductRepository {
    void save(OrderProduct orderProduct);
}
