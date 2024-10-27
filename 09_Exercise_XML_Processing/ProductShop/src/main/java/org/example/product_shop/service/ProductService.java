package org.example.product_shop.service;

import java.math.BigDecimal;

public interface ProductService {
    void seedProducts() throws Exception;

    void exportProductsBetweenPricesWithoutBuyer(BigDecimal low, BigDecimal high) throws Exception;
}
