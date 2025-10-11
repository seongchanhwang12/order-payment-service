package dev.chan.orderpaymentservice.application.product;

import dev.chan.orderpaymentservice.domain.product.Product;
import dev.chan.orderpaymentservice.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Product 조회시 레파지토티에게 조회 메시지를 전달하는 헬퍼입니다.
 * 추후 페이징 또는 검색 옵션 추가시 ProductQueryUseCase 로 승격하거나 별도로 product 조회가 추가될 수 있습니다.
 *
 */
@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> listAll(){
        return productRepository.findAll();
        // TODO 추후 페이징, 검색 조건 로직 추가 예정입니다.
    }

}
