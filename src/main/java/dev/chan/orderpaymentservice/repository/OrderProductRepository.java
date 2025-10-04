package dev.chan.orderpaymentservice.repository;

import dev.chan.orderpaymentservice.domain.OrderProduct;

public interface OrderProductRepository {
    void save(OrderProduct orderProduct);
}
