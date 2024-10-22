package org.example.product_shop.service;

import java.io.FileNotFoundException;

public interface CategoryService {
    void categorySeedData() throws FileNotFoundException;

    String getCategoriesByProductCount();
}
