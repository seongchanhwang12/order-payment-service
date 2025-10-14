package dev.chan.orderpaymentservice.repository.order;

import dev.chan.orderpaymentservice.application.order.GetOrderUseCase;
import dev.chan.orderpaymentservice.application.order.dto.OrderDetail;
import dev.chan.orderpaymentservice.application.order.dto.OrderHeader;
import dev.chan.orderpaymentservice.application.order.dto.OrderLine;
import dev.chan.orderpaymentservice.common.ErrorCode;
import dev.chan.orderpaymentservice.common.NotFoundException;
import dev.chan.orderpaymentservice.domain.common.Money;
import dev.chan.orderpaymentservice.domain.order.exception.OrderError;
import dev.chan.orderpaymentservice.domain.order.port.OrderProductQueryRepository;
import dev.chan.orderpaymentservice.domain.order.port.OrderQueryRepository;
import dev.chan.orderpaymentservice.repository.order.query.OrderProductProjection;
import dev.chan.orderpaymentservice.repository.order.query.OrderProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class GetOrderUseCaseTest {

    @InjectMocks GetOrderUseCase sut;
    @Mock OrderQueryRepository orderQueryRepository;

    @Test
    void 주문상세_조회_성공시_DTO를_반환한다() {
        long orderId = 1L;
        OrderDetail stub = new OrderDetail(
                new OrderHeader(orderId, 10L, LocalDateTime.now(), new BigDecimal("10000")),
                List.of(new OrderLine(100L, 1L, "상품", 10, new BigDecimal(10), new BigDecimal(10)))
        );

        given(orderQueryRepository.findDetailById(orderId)).willReturn(Optional.of(stub));

        OrderDetail result = sut.handle(orderId);

        assertThat(result).isNotNull();
        assertThat(result.orderHeader().orderId()).isEqualTo(orderId);
        then(orderQueryRepository).should(times(1)).findDetailById(orderId);
    }

    @Test
    void 주문상세가_없으면_NotFoundException을_던진다() {
        long orderId = 999L;
        given(orderQueryRepository.findDetailById(orderId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> sut.handle(orderId))
                .isInstanceOf(NotFoundException.class)
                .extracting("errorCode")
                .isEqualTo(OrderError.NOT_FOUND);


        then(orderQueryRepository).should(times(1)).findDetailById(orderId);
        then(orderQueryRepository).shouldHaveNoMoreInteractions();
    }

}