package com.example.advquerying.service;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    void getAllShampoosByGivenSizeOrderedById(String size);

    void getAllShampoosByGivenSizeAndLabelIdOrderedByPrice(String size, long id);

    void getAllShampoosByPriceHigherThan(BigDecimal price);

    void getAllShampoosByLessThanGivenPrice(BigDecimal price);

    void getAllShampoosByGivenListWithIngredients(List<String> shampoos);

    void getAllShampoosByIngredientsCount(int count);
}
