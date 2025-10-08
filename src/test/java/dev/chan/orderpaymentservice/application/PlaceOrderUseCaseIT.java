package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.dto.OrderResult;
import dev.chan.orderpaymentservice.application.dto.PlaceOrderCommand;
import dev.chan.orderpaymentservice.common.CommandMother;
import dev.chan.orderpaymentservice.domain.InsufficientStockException;
import dev.chan.orderpaymentservice.domain.ProductMother;
import dev.chan.orderpaymentservice.domain.common.Money;
import dev.chan.orderpaymentservice.domain.order.Order;
import dev.chan.orderpaymentservice.domain.order.OrderProduct;
import dev.chan.orderpaymentservice.domain.order.OrderProductRepository;
import dev.chan.orderpaymentservice.domain.order.OrderRepository;
import dev.chan.orderpaymentservice.domain.product.Product;
import dev.chan.orderpaymentservice.domain.product.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
public class PlaceOrderUseCaseIT {

    @PersistenceContext EntityManager em;

    @Autowired PlaceOrderUseCase sut;
    @Autowired ProductRepository productRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderProductRepository orderProductRepository;

    /**
     * Product stub
     * @return
     */
    private Product newProduct(int price, int stock){
        Product product = ProductMother.newProduct("keyboard", price, stock);
        productRepository.save(product);
        return product;
    }


    /**
     * 주문 생성 단건 통합테스트 (해피 패스)
     *
     * 설명:
     * - 주문 생성 프로세스의 성공 케이스에 대한 통합 테스트입니다.
     * - 이 테스트에선 usecase와 respository 연계에 대해서만 다룹니다.
     * - web 계층 (controller, controllerAdvice) 에 대한 테스트는 별도 작성 예정입니다.
     *
     * 테스트 목적:
     * - 계층간 협력이 의도한 대로 연계되는지 테스트합니다.
     *
     * 검증 포인트:
     * 1. 전달 받은 command 의 productId로 Product가 조회되어야합니다.
     * 2. 주문 결과의 orderId로 Order를 조회해 정상적으로 레파지토리에 저장 되어있는지 검증합니다.
     * 3. OrderId로 주문 제품이 정상적으로 레파지토레이 저장 되었는지 검증합니다.
     * 4. Product의 stockQuantity가 command의 OrderQuantity 만큼 감소  되어야합니다.
     * 5. 결과 합계가 orderProduct의 주문금액 총합과 일치해야 합니다.
     */
    @Test
    void 주문생성요청시_제품데이터를조회하고_주문_주문상품_데이터를저장한다 () {
        //given
        int price = 15000;
        int quantity = 10;
        Product product = newProduct(price, quantity);
        PlaceOrderCommand cmd = CommandMother.withId(product.getId());

        //when
        OrderResult orderResult = sut.handle(cmd);

        em.flush();
        em.clear();

        //then
        // --- 제품 검증 ---
        Product foundProduct = productRepository.findById(product.getId()).orElseThrow();
        int decreasedQuantity = quantity - cmd.orderQuantity();
        assertThat(foundProduct.getStockQuantity().value()).isEqualTo(decreasedQuantity);

        // --- 주문 검증 ---
        Optional<Order> foundOrder = orderRepository.findById(orderResult.orderId());
        assertThat(foundOrder).isPresent();

        Order order = foundOrder.get();
        assertThat(order.getOrderedBy()).isEqualTo(cmd.memberId());
        assertThat(order.getOrderedBy()).isEqualTo(orderResult.orderedBy());

        // --- 주문 제품 ---
        List<OrderProduct> foundOrderProducts = orderProductRepository.findAllByOrderId(orderResult.orderId());
        assertThat(orderResult).isNotNull();
        assertThat(orderResult.orderProducts()).isNotEmpty();
        assertThat(orderResult.orderProducts().size()).isEqualTo(foundOrderProducts.size());
        assertThat(orderResult.orderId()).isEqualTo(foundOrder.get().getId());

        // 주문 상세/합계 검증
        OrderProduct line = foundOrderProducts.get(0);
        assertThat(line.getOrderQuantity().value()).isEqualTo(cmd.orderQuantity());
        assertThat(line.getProductId()).isEqualTo(foundProduct.getId());

        Money totalPriceByOrderProduct = product.getPrice().multiply(cmd.orderQuantity());
        assertThat(line.getProductTotalPriceAtOrder()).isEqualTo(totalPriceByOrderProduct);

        // 주문 총합
        Money liensTotal = foundOrderProducts.stream()
                .map(OrderProduct::getProductTotalPriceAtOrder)
                .reduce(Money.ZERO, Money::add);

        assertThat(orderResult.totalAmount().amount()).isEqualByComparingTo(liensTotal.amount());

    }

    /**
     * 주문 생성 단건 통합테스트 (언해피 패스)
     *
     * 설명:
     * - 주문 생성시 주문 수량 초과 예외에 대한 통합테스트입니다.
     * - InsufficientStockException 이 사실상 Product 조회 후 바로 발생하기 때문에 order와 orderProduct에 대한 롤백 테스트가 의미 없어 보일 수 있습니다.
     *  그럼에도 테스트를 작성한 이유는 요구사항은 계속 변경될 수 있기 때문입니다.
     *  요구사항이 변경되어 재고의 decrease가 orderProduct등 데이터 저장 로직 이후 발생 가능성이 있다고 생각했습니다.
     *  물론 요구사항이 변경되면 테스트도 수정 되어야 하지만, 요구사항 변경 시에도 변경 사항에 대해서만 신경 쓸 수 있도록, 발생 가능한 사이드이펙트를 사전에 차단합니다.
     *
     * 테스트 목적:
     * - 주문 생성시 주문 수량이 재고보다 많을 경우 예외 발생 및 데이터 롤백을 테스트합니다.
     *
     * 검증 포인트:
     * 1. 주문 생성시 주문 수량이 재고 수량을 넘으면 InsufficientStockException이 발생해야 합니다.
     * 2. 예외 발생시 OrderRepository, OrderProductRepository 저장한 데이터가 롤백되어야 합니다.
     * 3. 예외 발생시 Product 에서 감소한 재고가 복구 되어야합니다.
     */
    @Test
    void 재고부족이면_주문실패하고_DB변경을_롤백한다() {
        //given
        int stock = 1;
        int price = 10000;
        Product product = newProduct(price, stock);
        PlaceOrderCommand cmd = CommandMother.withIdAndQuantity(product.getId(),stock + 1);

        //when & then
        long beforeOrderCount = orderRepository.count();
        long beforeOrderProductCount = orderProductRepository.count();

        assertThatThrownBy(()-> sut.handle(cmd))
                .isInstanceOf(InsufficientStockException.class);

        em.flush();
        em.clear();

        // 주문,주문제품 롤백
        assertThat(orderRepository.count()).isEqualTo(beforeOrderCount);
        assertThat(orderProductRepository.count()).isEqualTo(beforeOrderProductCount);

        // 재고 감소 안됨
        Product foundProduct = productRepository.findById(product.getId()).orElseThrow();
        assertThat(foundProduct.getStockQuantity().value()).isEqualTo(stock);
    }




}
