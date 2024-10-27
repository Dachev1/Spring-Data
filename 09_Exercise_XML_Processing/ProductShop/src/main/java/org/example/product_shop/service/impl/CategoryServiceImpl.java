package org.example.product_shop.service.impl;

import org.example.product_shop.data.entities.Category;
import org.example.product_shop.data.repositories.CategoryRepository;
import org.example.product_shop.service.CategoryService;
import org.example.product_shop.service.dtos.exportDtos.category.CategoryWithProductStatsDto;
import org.example.product_shop.service.dtos.exportDtos.category.CategoryWithProductStatsRootDto;
import org.example.product_shop.service.dtos.importDtos.category.CategoryDto;
import org.example.product_shop.service.dtos.importDtos.category.CategoryRootDto;
import org.example.product_shop.util.validation.ValidatorComponent;
import org.example.product_shop.util.xmlmapper.XMLMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final static String FILE_IMPORT_PATH = "src/main/resources/xml/imports/categories.xml";
    private final static String FILE_EXPORT_PATH = "src/main/resources/xml/exports/categories-by-products.xml";

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidatorComponent validatorComponent;
    private final XMLMapperService xmlMapperService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidatorComponent validatorComponent, XMLMapperService xmlMapperService) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validatorComponent = validatorComponent;
        this.xmlMapperService = xmlMapperService;
    }

    @Override
    public void seedCategories() throws Exception {
        if (this.categoryRepository.count() == 0) {
            CategoryRootDto categoryRootDto = this.xmlMapperService.unmarshal(CategoryRootDto.class, FILE_IMPORT_PATH);

            for (CategoryDto categoryDto : categoryRootDto.getCategoryDtoList()) {
                if (!this.validatorComponent.isValid(categoryDto)) {
                    this.validatorComponent.validate(categoryDto).forEach(System.out::println);
                    continue;
                }

                this.categoryRepository.saveAndFlush(this.modelMapper.map(categoryDto, Category.class));
            }
        }
    }

    @Override
    public void exportCategoriesByProductStats() throws Exception {
        List<CategoryWithProductStatsDto> categories = this.categoryRepository.findAll().stream()
                .map(category -> {
                    CategoryWithProductStatsDto dto = this.modelMapper.map(category, CategoryWithProductStatsDto.class);
                    dto.setProductsCount(category.getProducts().size());
                    dto.setTotalRevenue(calculateTotalRevenue(category));
                    dto.setAveragePrice(calculateAveragePrice(dto.getTotalRevenue(), dto.getProductsCount()));
                    return dto;
                })
                .sorted((a, b) -> Integer.compare(b.getProductsCount(), a.getProductsCount()))
                .collect(Collectors.toList());

        CategoryWithProductStatsRootDto rootDto = new CategoryWithProductStatsRootDto();
        rootDto.setCategories(categories);

        this.xmlMapperService.marshal(rootDto, FILE_EXPORT_PATH);
    }

    private BigDecimal calculateTotalRevenue(Category category) {
        return category.getProducts().stream()
                .map(product -> product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateAveragePrice(BigDecimal totalRevenue, int productCount) {
        return productCount > 0
                ? totalRevenue.divide(BigDecimal.valueOf(productCount), 5, BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;
    }
}