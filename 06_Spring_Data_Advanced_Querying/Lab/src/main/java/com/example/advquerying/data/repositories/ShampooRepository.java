package com.example.advquerying.data.repositories;

import com.example.advquerying.data.entities.Shampoo;
import com.example.advquerying.data.entities.enums.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    Set<Shampoo> getAllBySizeOrderById(Size size);

    Set<Shampoo> getAllBySizeOrLabelIdOrderByPrice(Size size, long labelId);

    Set<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Set<Shampoo> findAllByPriceLessThan(BigDecimal price);

    @Query("SELECT DISTINCT s FROM Shampoo s JOIN s.ingredients i WHERE i.name IN :names GROUP BY s")
    Set<Shampoo> findDistinctShampoosByIngredientsNameIn(@Param("names") List<String> names);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i GROUP BY s HAVING COUNT(i) < :ingredientCount")
    List<Shampoo> findShampoosByIngredientsCountLessThan(@Param("ingredientCount") long ingredientCount);
}
