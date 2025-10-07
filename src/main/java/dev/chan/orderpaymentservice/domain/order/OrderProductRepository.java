package dev.chan.orderpaymentservice.domain.order;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository {
    void save(OrderProduct orderProduct);

    Optional<OrderProduct> findById(Long id);

    List<OrderProduct> findAllByOrderId(Long aLong);
}
