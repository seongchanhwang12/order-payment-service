package dev.chan.orderpaymentservice.repository.order.adapter;

import dev.chan.orderpaymentservice.application.order.dto.OrderDetail;
import dev.chan.orderpaymentservice.application.order.dto.OrderHeader;
import dev.chan.orderpaymentservice.application.order.dto.OrderLine;
import dev.chan.orderpaymentservice.domain.order.port.OrderQueryRepository;
import dev.chan.orderpaymentservice.repository.order.JpaOrderProductQueryRepository;
import dev.chan.orderpaymentservice.repository.order.JpaOrderQueryRepository;
import dev.chan.orderpaymentservice.repository.order.query.OrderProductProjection;
import dev.chan.orderpaymentservice.repository.order.query.OrderProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaOrderQueryRepositoryAdapter implements OrderQueryRepository {

    private final JpaOrderQueryRepository orderQueryRepository;
    private final JpaOrderProductQueryRepository orderProductQueryRepository;

    @Override
    public Optional<OrderDetail> findDetailById(long orderId) {
        Optional<OrderProjection> orderHeaderInfo = orderQueryRepository
                .findHeaderById(orderId);
        if(orderHeaderInfo.isEmpty()) return Optional.empty();

        OrderProjection orderProjection = orderHeaderInfo.get();
        OrderHeader orderHeader = new OrderHeader(orderProjection.getId(),
                orderProjection.getOrderedBy(),
                orderProjection.getOrderedAt(),
                orderProjection.getTotalPriceAmount());

        List<OrderProductProjection> orderLineInfo = orderProductQueryRepository.findProductsByOrderId(orderId);
        List<OrderLine> orderLines = orderLineInfo.stream()
                .map(op -> OrderLine.of(
                        op.getId(),
                        op.getProductId(),
                        op.getProductName(),
                        op.getQuantity(),
                        op.productPrice(),
                        op.totalPriceAmount())).toList();

        return Optional.of(new OrderDetail(orderHeader,orderLines));
    }

}
