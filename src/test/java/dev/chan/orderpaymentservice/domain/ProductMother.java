package dev.chan.orderpaymentservice.domain;

import dev.chan.orderpaymentservice.domain.common.Money;
import dev.chan.orderpaymentservice.domain.common.Quantity;
import dev.chan.orderpaymentservice.domain.product.Product;

/**
 * Product 스텁 팩토리
 */
public class ProductMother {
    // 공통 상수 정의
    private static final String NAME = "Product";
    private static final int STOCK_QUANTITY = 10;
    private static final long PRICE = 10_000L;
    private static final String DESCRIPTION = "Product description";
    private static final long SELLER_ID = 1L;

    /**
     * 기본 값 스텁객체 반환
     * @return Product
     */
    public static Product any(){
        return Product.of(
                NAME,
                Money.wons(PRICE),
                DESCRIPTION,
                SELLER_ID,
                Quantity.of(STOCK_QUANTITY)
        );
    }

    /**
     * 지정된 재고 수량을 가진 스텁 객체 반환
     * @param stockQuantity - 재고 수량
     * @return Product
     */
    public static Product withStock(int stockQuantity) {
        return Product.of(
                NAME,
                Money.wons(PRICE),
                DESCRIPTION,
                SELLER_ID,
                Quantity.of(stockQuantity)
        );
    }

    public static Product newProduct(String name, int money, int quantity) {
        return Product.of(
                name,
                Money.wons(money),
                DESCRIPTION,
                SELLER_ID,
                Quantity.of(quantity)
        );
    }
}
