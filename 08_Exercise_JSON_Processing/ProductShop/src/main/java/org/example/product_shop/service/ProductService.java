package org.example.product_shop.service;

import org.example.product_shop.service.dtos.exportDtos.user.UserSoldProductsDto;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProductData() throws FileNotFoundException;

    String exportProductsInRange(BigDecimal minPrice, BigDecimal maxPrice);

    String getUsersWithSoldProducts();
}
