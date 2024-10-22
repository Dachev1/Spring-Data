package org.example.product_shop.service.impl;

import com.google.gson.Gson;
import org.example.product_shop.data.entities.Category;
import org.example.product_shop.data.entities.Product;
import org.example.product_shop.data.entities.User;
import org.example.product_shop.data.repositories.CategoryRepository;
import org.example.product_shop.data.repositories.ProductRepository;
import org.example.product_shop.data.repositories.UserRepository;
import org.example.product_shop.service.ProductService;
import org.example.product_shop.service.dtos.exportDtos.product.ProductSoldDto;
import org.example.product_shop.service.dtos.exportDtos.product.ProductsDtoInRange;
import org.example.product_shop.service.dtos.exportDtos.user.UserSoldProductsDto;
import org.example.product_shop.service.dtos.importDtos.product.ProductSeedDto;
import org.example.product_shop.util.ValidatorComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final static String FILE_PATH = "src/main/resources/01_json_input_data/products.json";
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorComponent validator;

    public ProductServiceImpl(ProductRepository productRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository,
                              ModelMapper modelMapper,
                              Gson gson,
                              ValidatorComponent validator) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public void seedProductData() throws FileNotFoundException {
        if (this.productRepository.count() != 0) {
            return;
        }

        ProductSeedDto[] productSeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), ProductSeedDto[].class);

        // Fetch all categories once
        List<Category> allCategories = categoryRepository.findAll();
        if (allCategories.isEmpty()) {
            throw new IllegalStateException("No categories found in the database");
        }

        for (ProductSeedDto productDto : productSeedDtos) {
            if (!this.validator.isValid(productDto)) {
                this.validator.validate(productDto)
                        .forEach(error -> System.out.println(error.getMessage()));
                continue;
            }

            Product product = this.modelMapper.map(productDto, Product.class);
            product.setSeller(getRandomUser(true));  // seller is required
            product.setBuyer(getRandomUser(false));  // buyer can be null
            product.setCategories(getRandomCategories(allCategories));

            this.productRepository.saveAndFlush(product);
        }

        System.out.println("The data for products was seeded");
    }

    @Override
    public String exportProductsInRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(minPrice, maxPrice);

        List<ProductsDtoInRange> productsDto = products.stream()
                .map(product -> new ProductsDtoInRange(
                        product.getName(),
                        product.getPrice(),
                        product.getSeller().getFirstName() + " " + product.getSeller().getLastName()
                )).collect(Collectors.toList());

        return this.gson.toJson(productsDto);
    }

    @Override
    public String getUsersWithSoldProducts() {
        List<User> users = userRepository.findAllUsersWithSoldProducts();

        List<UserSoldProductsDto> userSoldProductsDtoList = users.stream().map(user -> {
            List<ProductSoldDto> soldProducts = user.getSoldProducts().stream()
                    .filter(product -> product.getBuyer() != null)
                    .map(product -> modelMapper.map(product, ProductSoldDto.class))
                    .collect(Collectors.toList());

            UserSoldProductsDto userSoldProductsDto = modelMapper.map(user, UserSoldProductsDto.class);
            userSoldProductsDto.setSoldProducts(soldProducts);

            return userSoldProductsDto;
        }).collect(Collectors.toList());

        return this.gson.toJson(userSoldProductsDtoList);
    }

    private User getRandomUser(boolean isRequired) {
        if (!isRequired && ThreadLocalRandom.current().nextBoolean()) {
            return null;
        }

        long minId = 1;
        long maxId = userRepository.count();
        if (maxId == 0) {
            throw new IllegalStateException("No users found in the database");
        }

        long randomUserId = ThreadLocalRandom.current().nextLong(minId, maxId + 1);

        return userRepository.findById(randomUserId)
                .orElseThrow(() -> new IllegalStateException("User not found with ID: " + randomUserId));
    }

    private Set<Category> getRandomCategories(List<Category> allCategories) {
        int numberOfCategories = ThreadLocalRandom.current().nextInt(1, 5);
        return ThreadLocalRandom.current()
                .ints(0, allCategories.size())
                .distinct()
                .limit(numberOfCategories)
                .mapToObj(allCategories::get)
                .collect(Collectors.toSet());
    }
}
