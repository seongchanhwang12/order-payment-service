package dev.chan.orderpaymentservice.repository.order;

import dev.chan.orderpaymentservice.domain.order.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaOrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("select op from OrderProduct op where op.order.id = :orderId")
    List<OrderProduct> findAllByOrderId(Long orderId);
}
