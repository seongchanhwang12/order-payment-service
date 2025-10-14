package dev.chan.orderpaymentservice.application.payment;

import dev.chan.orderpaymentservice.domain.order.OrderMother;
import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.domain.order.port.OrderRepository;
import dev.chan.orderpaymentservice.domain.payment.Payment;
import dev.chan.orderpaymentservice.domain.payment.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayOrderUseCaseTest {

    @InjectMocks
    PayOrderUseCase sut;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    @Captor
    ArgumentCaptor<Payment> paymentCaptor;

    @Test
    void 정상적인_주문에대한_결재요청시_결제요청이_성공한다() {
        //given
        long orderId = 1L;
        Order order = OrderMother.any();
        PayOrderCommand cmd = new PayOrderCommand(orderId);

        doNothing().when(paymentRepository).save(any(Payment.class));
        doReturn(Optional.of(order)).when(orderRepository).findById(orderId);

        //when
        PaymentResult result = sut.handle(cmd);

        //then
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());
        //Payment payment = paymentCaptor.capture();
        //assertThat(payment).isNotNull();



    }
}