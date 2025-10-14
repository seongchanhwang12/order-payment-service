package dev.chan.orderpaymentservice.application.order;

import dev.chan.orderpaymentservice.application.order.dto.OrderDetail;
import dev.chan.orderpaymentservice.common.NotFoundException;
import dev.chan.orderpaymentservice.domain.order.exception.OrderError;
import dev.chan.orderpaymentservice.domain.order.port.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetOrderUseCase {
    private final OrderQueryRepository orderQueryRepository;

    public OrderDetail handle(long orderId) {
        return orderQueryRepository.findDetailById(orderId)
                .orElseThrow(() -> new NotFoundException(OrderError.NOT_FOUND, "can not found order. orderId=[" + orderId + "]"));

    }
}
