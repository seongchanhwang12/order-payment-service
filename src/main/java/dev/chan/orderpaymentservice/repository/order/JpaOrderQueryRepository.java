package dev.chan.orderpaymentservice.repository.order;

import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.repository.order.query.OrderProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaOrderQueryRepository extends Repository<Order, Long> {

    @Query("""
        select
            o.id as id,
            o.orderedBy as orderedBy,
            o.orderedAt as orderedAt,
            o.totalPrice.amount as totalPriceAmount
        from Order o
        where o.id in :orderId
        """)
    Optional<OrderProjection> findHeaderById(@Param("orderId") Long orderId);
}
