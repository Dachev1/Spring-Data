package org.example.product_shop.service.impl;

import com.google.gson.Gson;
import org.example.product_shop.data.entities.Category;
import org.example.product_shop.data.entities.Product;
import org.example.product_shop.data.repositories.CategoryRepository;
import org.example.product_shop.service.CategoryService;
import org.example.product_shop.service.dtos.exportDtos.category.CategoryStatsDto;
import org.example.product_shop.service.dtos.importDtos.category.CategorySeedDto;
import org.example.product_shop.util.ValidatorComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final static String FILE_PATH = "src/main/resources/01_json_input_data/categories.json";
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorComponent validator;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Gson gson, ValidatorComponent validator) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public void categorySeedData() throws FileNotFoundException {
        if (this.categoryRepository.count() != 0) {
            return;
        }

        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), CategorySeedDto[].class);

        for (CategorySeedDto categoryDto : categorySeedDtos) {
            if (!this.validator.isValid(categoryDto)) {
                this.validator.validate(categoryDto)
                        .forEach(error -> System.out.println(error.getMessage()));
                continue;
            }

            this.categoryRepository.saveAndFlush(this.modelMapper.map(categoryDto, Category.class));
        }
    }

    @Override
    public String getCategoriesByProductCount() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryStatsDto> categoryStatsDtos = categories.stream().map(category -> {
            List<Product> products = category.getProducts().stream().toList();
            long productCount = products.size();

            BigDecimal totalRevenue = products.stream()
                    .map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal averagePrice = productCount > 0 ?
                    totalRevenue.divide(BigDecimal.valueOf(productCount), 6, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            return new CategoryStatsDto(
                    category.getName(),
                    productCount,
                    averagePrice,
                    totalRevenue
            );
        }).sorted(Comparator.comparing(CategoryStatsDto::getProductCount).reversed()).collect(Collectors.toList());

        return this.gson.toJson(categoryStatsDtos);
    }
}