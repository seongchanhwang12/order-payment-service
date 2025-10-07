package dev.chan.orderpaymentservice.repository.order;

import dev.chan.orderpaymentservice.domain.order.OrderProduct;
import dev.chan.orderpaymentservice.domain.order.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaOrderProductRepositoryAdapter implements OrderProductRepository {
    private final JpaOrderProductRepository orderProductRepository;


    @Override
    public void save(OrderProduct orderProduct) {
        orderProductRepository.save(orderProduct);
    }

    @Override
    public Optional<OrderProduct> findById(Long id) {
        return orderProductRepository.findById(id);
    }

    @Override
    public List<OrderProduct> findAllByOrderId(Long orderId) {
        return orderProductRepository.findAllByOrderId(orderId);
    }

}
