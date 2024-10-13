package com.example.advquerying.data.repositories;

import com.example.advquerying.data.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Set<Ingredient> getAllByNameStartsWith(String letter);

    Set<Ingredient> getAllByNameInOrderByPrice(List<String> names);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ingredient i WHERE i.name = :name")
    void deleteIngredientByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i SET i.price = i.price * 1.1")
    void increasePriceByTenPercent();

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i SET i.price = i.price + :price WHERE i.name IN :names")
    void updatePriceByNames(@Param("price") BigDecimal price, @Param("names") List<String> names);
}
