package dev.chan.orderpaymentservice.application.payment;


import dev.chan.orderpaymentservice.common.NotFoundException;
import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.domain.order.entity.OrderProduct;
import dev.chan.orderpaymentservice.domain.order.exception.OrderError;
import dev.chan.orderpaymentservice.domain.order.port.OrderProductRepository;
import dev.chan.orderpaymentservice.domain.order.port.OrderRepository;
import dev.chan.orderpaymentservice.domain.payment.Payment;
import dev.chan.orderpaymentservice.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayOrderUseCase {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentResult handle(PayOrderCommand cmd) {

        Order order = orderRepository.findById(cmd.orderId())
                .orElseThrow(() -> new NotFoundException(OrderError.NOT_FOUND, "can not found order. orderId = [" + cmd.orderId() + "]"));

        //Payment.paid(order.getId());
        //paymentRepository.save(payment);

        return null;
    }


}
