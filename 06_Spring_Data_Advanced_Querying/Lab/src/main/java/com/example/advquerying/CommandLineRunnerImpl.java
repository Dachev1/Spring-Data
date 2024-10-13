package com.example.advquerying;

import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    public CommandLineRunnerImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        //ex_01 this.shampooService.getAllShampoosByGivenSizeOrderedById("medium");
        //ex_02 this.shampooService.getAllShampoosByGivenSizeAndLabelIdOrderedByPrice("medium", 10);
        //ex_03 this.shampooService.getAllShampoosByPriceHigherThan(BigDecimal.valueOf(5));
        //ex_04 this.ingredientService.getAllIngredientsStartWithGivenLetter("M");
        //ex_05 this.ingredientService.getAllIngredientsFromList(List.of("Lavender", "Herbs", "Apple"));
        //ex_06 this.shampooService.getAllShampoosByLessThanGivenPrice(BigDecimal.valueOf(8.50));
        //ex_07 this.shampooService.getAllShampoosByGivenListWithIngredients(List.of("Berry", "Mineral-Collagen"));
        //ex_08 this.shampooService.getAllShampoosByIngredientsCount(2);
        //ex_09 this.ingredientService.deleteIngredientByName("Cherry");
        //ex_10 this.ingredientService.increaseEveryPriceByTenPercent();
        //ex_11 this.ingredientService.increaseThePriceOfGivenIngredientName(BigDecimal.valueOf(101), List.of("Apple"));
    }
}
