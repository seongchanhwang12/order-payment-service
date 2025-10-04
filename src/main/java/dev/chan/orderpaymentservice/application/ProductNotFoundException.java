package dev.chan.orderpaymentservice.application;

import dev.chan.orderpaymentservice.common.PolicyViolationException;
import dev.chan.orderpaymentservice.domain.ProductError;

public class ProductNotFoundException extends PolicyViolationException {

    public ProductNotFoundException(Long productId) {
        super(ProductError.NOT_FOUND, "Product not found : " + productId);
    }

}
