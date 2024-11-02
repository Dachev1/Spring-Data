package org.example.product_shop.service;

import java.io.FileNotFoundException;

public interface CategoryService {
    void seedCategories() throws Exception;

    void exportCategoriesByProductStats() throws Exception;
}
