package org.example.product_shop.data.repositories;

import org.example.product_shop.data.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetweenAndBuyerIsNotNullOrderByPriceAsc(BigDecimal low, BigDecimal high);
}
