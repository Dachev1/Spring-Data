package com.example.advquerying.service.impl;

import com.example.advquerying.data.entities.Shampoo;
import com.example.advquerying.data.entities.enums.Size;
import com.example.advquerying.data.repositories.ShampooRepository;
import com.example.advquerying.service.ShampooService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public void getAllShampoosByGivenSizeOrderedById(String size) {
        Size sizeEnum = Size.valueOf(size.toUpperCase());

        print(this.shampooRepository.getAllBySizeOrderById(sizeEnum));
    }

    @Override
    public void getAllShampoosByGivenSizeAndLabelIdOrderedByPrice(String size, long id) {
        Size sizeEnum = Size.valueOf(size.toUpperCase());

        print(this.shampooRepository.getAllBySizeOrLabelIdOrderByPrice(sizeEnum, id));
    }

    @Override
    public void getAllShampoosByPriceHigherThan(BigDecimal price) {
        print(this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price));
    }

    @Override
    public void getAllShampoosByLessThanGivenPrice(BigDecimal price) {
        System.out.println(this.shampooRepository.findAllByPriceLessThan(price).size());
    }

    @Override
    public void getAllShampoosByGivenListWithIngredients(List<String> ingredients ) {
        this.shampooRepository.findDistinctShampoosByIngredientsNameIn(ingredients)
                .forEach(s -> System.out.println(s.getBrand()));
    }

    @Override
    public void getAllShampoosByIngredientsCount(int count) {
        List<Shampoo> shampoos = this.shampooRepository.findShampoosByIngredientsCountLessThan(count);
        shampoos.forEach(s -> System.out.println(s.getBrand()));
    }

    private void print(Set<Shampoo> shampooRepository) {
        shampooRepository
                .forEach(s -> System.out.printf("%s %s %.2flv.\n", s.getBrand(), s.getSize(), s.getPrice()));
    }
}
