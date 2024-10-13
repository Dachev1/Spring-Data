package com.example.advquerying.service.impl;

import com.example.advquerying.data.repositories.IngredientRepository;
import com.example.advquerying.service.IngredientService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void getAllIngredientsStartWithGivenLetter(String letter) {
        this.ingredientRepository.getAllByNameStartsWith(letter)
                .forEach(i -> System.out.println(i.getName()));
    }

    @Override
    public void getAllIngredientsFromList(List<String> ingredients) {
        this.ingredientRepository.getAllByNameInOrderByPrice(ingredients)
                .forEach(i -> System.out.println(i.getName()));
    }

    @Override
    public void deleteIngredientByName(String name) {
        this.ingredientRepository.deleteIngredientByName(name);
    }

    @Override
    public void increaseEveryPriceByTenPercent() {
        this.ingredientRepository.increasePriceByTenPercent();
    }

    @Override
    public void increaseThePriceOfGivenIngredientName(BigDecimal price, List<String> names) {
        this.ingredientRepository.updatePriceByNames(price, names);
    }
}
