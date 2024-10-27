package org.example.product_shop;

import org.example.product_shop.service.CategoryService;
import org.example.product_shop.service.ProductService;
import org.example.product_shop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public CommandLineRunnerImpl(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.seedUsers();
        this.categoryService.seedCategories();
        this.productService.seedProducts();

        // this.productService.exportProductsBetweenPricesWithoutBuyer(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        //this.userService.exportUsersWithSoldProducts();
        //this.categoryService.exportCategoriesByProductStats();
        this.userService.exportUsersWithSalesData();
    }
}
