package org.example.product_shop.service.impl;

import org.example.product_shop.data.entities.Category;
import org.example.product_shop.data.entities.Product;
import org.example.product_shop.data.entities.User;
import org.example.product_shop.data.repositories.CategoryRepository;
import org.example.product_shop.data.repositories.ProductRepository;
import org.example.product_shop.data.repositories.UserRepository;
import org.example.product_shop.service.ProductService;
import org.example.product_shop.service.dtos.exportDtos.product.ProductExportDto;
import org.example.product_shop.service.dtos.exportDtos.product.ProductExportRootDto;
import org.example.product_shop.service.dtos.importDtos.product.ProductDto;
import org.example.product_shop.service.dtos.importDtos.product.ProductRootDto;
import org.example.product_shop.util.validation.ValidatorComponent;
import org.example.product_shop.util.xmlmapper.XMLMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final static String FILE_IMPORT_PATH = "src/main/resources/xml/imports/products.xml";
    private final static String FILE_EXPORT_PATH = "src/main/resources/xml/exports/products-in-range.xml";

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ValidatorComponent validatorComponent;
    private final ModelMapper modelMapper;
    private final XMLMapperService xmlMapperService;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ValidatorComponent validatorComponent, ModelMapper modelMapper, XMLMapperService xmlMapperService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.validatorComponent = validatorComponent;
        this.modelMapper = modelMapper;
        this.xmlMapperService = xmlMapperService;
    }

    @Override
    public void seedProducts() throws Exception {
        if (this.productRepository.count() == 0) {
            ProductRootDto productRootDto = this.xmlMapperService.unmarshal(ProductRootDto.class, FILE_IMPORT_PATH);
            List<Category> categories = this.categoryRepository.findAll();

            for (ProductDto productDto : productRootDto.getProductDtoList()) {
                if (!this.validatorComponent.isValid(productDto)) {
                    this.validatorComponent.validate(productDto).forEach(System.out::println);
                    continue;
                }

                Product product = this.modelMapper.map(productDto, Product.class);

                product.setSeller(getRandomSeller());
                product.setBuyer(getRandomBuyer());
                product.setCategories(getRandomCategories(categories));

                this.productRepository.saveAndFlush(product);
            }
        }
    }

    @Override
    public void exportProductsBetweenPricesWithoutBuyer(BigDecimal low, BigDecimal high) throws Exception {
        List<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNotNullOrderByPriceAsc(low, high);

        List<ProductExportDto> list = products.stream()
                .map(p -> {
                    ProductExportDto productExportDto = this.modelMapper.map(p, ProductExportDto.class);
                    String fullName = String.format("%s %s", p.getSeller().getFirstName(), p.getSeller().getLastName());
                    productExportDto.setSeller(fullName);
                    return productExportDto;
                })
                .toList();

        ProductExportRootDto productExportRootDto = new ProductExportRootDto();
        productExportRootDto.setProductExportDtoList(list);

        this.xmlMapperService.marshal(productExportRootDto, FILE_EXPORT_PATH);
    }

    private User getRandomSeller() {
        long count = this.userRepository.count();
        long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);

        return this.userRepository.findById(randomId).orElse(null);
    }

    private User getRandomBuyer() {
        long count = this.userRepository.count();
        long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);

        return ThreadLocalRandom.current().nextBoolean() ? this.userRepository.findById(randomId).orElse(null) : null;
    }

    private Set<Category> getRandomCategories(List<Category> categories) {
        int numberOfCategories = ThreadLocalRandom.current().nextInt(1, categories.size() + 1);
        return ThreadLocalRandom.current()
                .ints(0, categories.size())
                .distinct()
                .limit(numberOfCategories)
                .mapToObj(categories::get)
                .collect(Collectors.toSet());
    }
}
