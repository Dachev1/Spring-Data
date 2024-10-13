package com.example.advquerying.service;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {
    void getAllIngredientsStartWithGivenLetter(String letter);

    void getAllIngredientsFromList(List<String> ingredients);

    void deleteIngredientByName(String name);

    void increaseEveryPriceByTenPercent();

    void increaseThePriceOfGivenIngredientName(BigDecimal price, List<String> names);
}
