package org.example.bookshop_system.service;

import org.example.bookshop_system.data.entities.Category;

import java.io.IOException;
import java.util.Set;


public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
