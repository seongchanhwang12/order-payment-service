package dev.chan.orderpaymentservice.repository.order;

import dev.chan.orderpaymentservice.domain.order.entity.OrderProduct;
import dev.chan.orderpaymentservice.repository.order.query.OrderProductProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaOrderProductQueryRepository extends Repository<OrderProduct, Long> {

    @Query("""
        select 
            op.id as id,
            op.productId as productId,
            op.productTotalPriceAtOrder.amount as totalPriceAmount,
            op.productPrice.amount as productPrice,
            op.productName as productName,
            op.orderQuantity.value as quantity                
        from OrderProduct op
        where  op.order.id=:orderId
    """)
    List<OrderProductProjection> findProductsByOrderId(@Param("orderId") long orderId);
}
