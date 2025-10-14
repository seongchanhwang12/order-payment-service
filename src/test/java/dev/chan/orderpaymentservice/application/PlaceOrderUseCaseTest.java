package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.order.dto.OrderResult;
import dev.chan.orderpaymentservice.application.order.dto.PlaceOrderCommand;
import dev.chan.orderpaymentservice.application.order.PlaceOrderUseCase;
import dev.chan.orderpaymentservice.application.product.ProductNotFoundException;
import dev.chan.orderpaymentservice.common.PlaceOrderCommandMother;
import dev.chan.orderpaymentservice.domain.common.Money;
import dev.chan.orderpaymentservice.domain.product.InsufficientStockException;
import dev.chan.orderpaymentservice.domain.product.ProductMother;
import dev.chan.orderpaymentservice.domain.order.entity.Order;
import dev.chan.orderpaymentservice.domain.order.entity.OrderProduct;
import dev.chan.orderpaymentservice.domain.product.Product;
import dev.chan.orderpaymentservice.domain.order.port.OrderProductRepository;
import dev.chan.orderpaymentservice.domain.order.port.OrderRepository;
import dev.chan.orderpaymentservice.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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

    /**
     * 정상 주문 성공 (해피 패스)
     *
     * 설명:
     * 사용자가 상품을 정상적으로 주문하는 경우,
     * - 주문(Order)이 생성되고
     * - 주문상품(OrderProduct)이 추가되며
     * - 상품(Product)의 재고가 주문 수량만큼 감소해야 합니다.
     *
     * 테스트 목적:
     * - 주문 생성 플로우의 기본 동작을 확인합니다.
     * - 도메인 간 연계 로직이 정상적으로 수행되는지를 검증합니다.
     *
     * 검증 포인트:
     * 1. Order와 OrderProduct가 정상적으로 생성되어야 합니다.
     * 2. Product의 재고가 주문 수량만큼 감소해야 합니다.
     * 3. 총 주문 금액이 올바르게 계산되어야 합니다.
     */
    @Test
    void 사용자가상품을주문하면_주문이생성되고_재고가감소한다() {
        long memberId = 1L;
        int stockQuantity = 5;
        int orderQuantity = 3;

        Money productPrice = new Money(BigDecimal.valueOf(10000));
        Product product = ProductMother.withStock(stockQuantity);
        PlaceOrderCommand cmd = PlaceOrderCommandMother.withIdAndQuantity(product.getId(),orderQuantity);

        // given

        when(productRepository.findById(cmd.productId())).thenReturn(Optional.of(product));
        doNothing().when(orderRepository).save(any(Order.class));
        doNothing().when(orderProductRepository).save(any(OrderProduct.class));

        // when
        OrderResult result = sut.handle(cmd);

        // then
        verify(productRepository).findById(cmd.productId());
        verify(orderRepository).save(orderCaptor.capture());
        verify(orderProductRepository).save(orderProductCaptor.capture());

        Order order = orderCaptor.getValue();
        assertThat(order).isNotNull();
        assertThat(order.getOrderedBy()).isEqualTo(memberId);

        OrderProduct orderProduct = orderProductCaptor.getValue();
        assertThat(orderProduct).isNotNull();

        assertThat(product.getStockQuantity().value()).isEqualTo(stockQuantity - cmd.orderQuantity());
        assertThat(result.totalAmount()).isEqualTo(productPrice.multiply(cmd.orderQuantity()));

    }


    /**
     * 존재하지 않는 상품으로 주문할 경우 예외 발생 테스트
     *
     * 설명:
     * - ProductRepository에서 상품을 찾을 수 없는 경우
     * - ProductNotFoundException이 발생해야 합니다.
     *
     * 테스트 목적:
     * - 잘못된 productId가 입력된 경우 예외 처리가 올바르게 동작하는지 검증합니다.
     *
     * 검증 포인트:
     * 1. ProductNotFoundException이 발생해야 합니다.
     * 2. Order 및 OrderProduct 저장 로직이 호출되지 않아야 합니다.
     */
    @Test
    void 커맨드의productId로_조회시_Product가_없으면_ProductNotFoundException을_던진다() {
        //given
        PlaceOrderCommand cmd = PlaceOrderCommandMother.placeOrderCommand();

        //when & then
        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(()-> sut.handle(cmd))
                .withMessageContaining(String.valueOf(cmd.productId()));

        verifyNoInteractions(orderRepository, orderProductRepository);

    }

    /**
     * 주문 수량이 재고보다 많은 경우
     *
     * 설명:
     * - Product의 재고가 부족할 경우 주문이 실패해야 합니다.
     * - Product.decreaseStock() 호출 시 InsufficientStockException이 발생해야 합니다.
     *
     * 테스트 목적:
     * - Product 도메인의 재고 검증 로직 불변식이 올바르게 동작하는지 확인합니다.
     *
     * 검증 포인트:
     * 1. InsufficientStockException이 발생해야 합니다.
     * 2. Order 및 OrderProduct는 저장되지 않아야 합니다.
     */
    @Test
    void 주문수량이_재고수량보다_많으면_IllegalArgumentException을_던진다(){
        // given
        int stockQuantity = 5;
        int orderQuantity = 10;

        Product product = ProductMother.withStock(stockQuantity);
        PlaceOrderCommand cmd = PlaceOrderCommandMother.withIdAndQuantity(product.getId(),orderQuantity);

        doReturn(Optional.of(product)).when(productRepository).findById(any());

        // when & then
        assertThatThrownBy(()-> sut.handle(cmd))
                .isInstanceOf(InsufficientStockException.class);

        verifyNoInteractions(orderRepository, orderProductRepository);

    }

    /**
     * 주문 수량이 음수인 경우
     *
     * 설명:
     * - Quantity의 불변식 위반으로 인해 IllegalArgumentException이 발생해야 합니다.
     * - 이는 애플리케이션 정책 차원의 입력 검증에 해당합니다.
     *
     * 테스트 목적:
     * - Quantity.of(...) 생성 시 불변식이 올바르게 검증되는지 확인합니다.
     *
     * 검증 포인트:
     * 1. IllegalArgumentException이 발생해야 합니다.
     * 2. 예외 메시지에 잘못된 입력값이 포함되어야 합니다.
     */
    @Test
    void 주문수량이_음수이면_IllegalArgumentException을_던진다() {
        // given
        int orderQuantity = -1;
        PlaceOrderCommand cmd = PlaceOrderCommandMother.withQuantity(orderQuantity);

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> sut.handle(cmd))
                .withMessageContaining(String.valueOf(cmd.orderQuantity()));
    }


    /**
     * 재고 수량과 주문 수량이 동일한 경우
     *
     * 설명:
     * - 주문 수량(orderQuantity)과 재고 수량(stockQuantity)이 동일한 경우,
     *   주문은 정상적으로 처리되어야 합니다.
     * - 예외가 발생하지 않아야 하며, 주문 후 재고는 정확히 0이어야 합니다.
     *
     * 테스트 목적:
     * - 경계 조건(boundary condition)에서의 정상 동작을 검증합니다.
     *
     * 검증 포인트:
     * 1. sut.handle(cmd)가 예외 없이 실행되어야 합니다.
     * 2. Product의 재고(stockQuantity)는 0으로 감소해야 합니다.
     */
    @Test
    void 재고와_주문수량이_같으면_정상_처리된다() {
        Product mac = ProductMother.withStock(5);
        PlaceOrderCommand cmd = PlaceOrderCommandMother.withIdAndQuantity(mac.getId(), 5);
        when(productRepository.findById(cmd.productId())).thenReturn(Optional.of(mac));

        assertThatCode(() -> sut.handle(cmd)).doesNotThrowAnyException();
        assertThat(mac.getStockQuantity().value()).isEqualTo(0);
    }

}