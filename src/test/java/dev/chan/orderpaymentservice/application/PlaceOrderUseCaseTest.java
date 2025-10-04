package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.dto.OrderResult;
import dev.chan.orderpaymentservice.application.dto.PlaceOrderCommand;
import dev.chan.orderpaymentservice.domain.Money;
import dev.chan.orderpaymentservice.domain.Order;
import dev.chan.orderpaymentservice.domain.OrderProduct;
import dev.chan.orderpaymentservice.domain.Product;
import dev.chan.orderpaymentservice.repository.OrderProductRepository;
import dev.chan.orderpaymentservice.repository.OrderRepository;
import dev.chan.orderpaymentservice.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceOrderUseCaseTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderProductRepository orderProductRepository;

    @Captor
    ArgumentCaptor<Order> orderCaptor;

    @Captor
    ArgumentCaptor<OrderProduct> orderProductCaptor;

    @InjectMocks
    PlaceOrderUseCase sut;

    @Test
    void 사용자가상품을주문하면_주문이생성되고_재고가감소한다() {
        long memberId = 1L;
        long productId = 1L;
        int orderQuantity = 2;
        int productQuantity = 5;
        Money productPrice = new Money(BigDecimal.valueOf(10000));

        // given
        Product macBook = new Product(productId, "Macbook", productPrice,"애플사의 노트북", memberId, productQuantity);
        PlaceOrderCommand cmd = new PlaceOrderCommand(memberId, productId, orderQuantity);

        when(productRepository.findById(productId)).thenReturn(Optional.of(macBook));
        doNothing().when(orderRepository).save(any(Order.class));
        doNothing().when(orderProductRepository).save(any(OrderProduct.class));

        // when
        OrderResult result = sut.handle(cmd);

        // then
        verify(productRepository).findById(productId);
        verify(orderRepository).save(orderCaptor.capture());

        Order order = orderCaptor.getValue();
        assertThat(order).isNotNull();
        assertThat(order.getOrderedBy()).isEqualTo(memberId);

        verify(orderProductRepository).save(orderProductCaptor.capture());
        OrderProduct orderProduct = orderProductCaptor.getValue();
        assertThat(orderProduct).isNotNull();
        assertThat(orderProduct.getProductId()).isEqualTo(productId);


        // 제품 수량 - 주문 수량 = 3
        // 주문 수량 = 2
        assertThat(macBook.getStockQuantity()).isEqualTo(productQuantity-orderQuantity);

        assertThat(result.totalAmount()).isEqualTo(productPrice.multiply(orderQuantity));


    }


}