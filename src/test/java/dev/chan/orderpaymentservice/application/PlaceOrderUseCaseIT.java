package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.application.dto.OrderResult;
import dev.chan.orderpaymentservice.application.dto.PlaceOrderCommand;
import dev.chan.orderpaymentservice.common.CommandMother;
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

@Slf4j
@SpringBootTest
@Transactional
public class PlaceOrderUseCaseIT {

    @PersistenceContext EntityManager em;

    @Autowired PlaceOrderUseCase placeOrderUseCase;
    @Autowired ProductRepository productRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderProductRepository orderProductRepository;

    /**
     * Product stub
     * @return
     */
    private Product newProduct(int price, int quantity){
        Product product = ProductMother.newProduct("keyboard", price, quantity);
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
        OrderResult orderResult = placeOrderUseCase.handle(cmd);

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




}
