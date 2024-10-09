package org.example.bookshop_system.service.impl;

import org.example.bookshop_system.data.entities.Category;
import org.example.bookshop_system.data.repositories.CategoryRepository;
import org.example.bookshop_system.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String FILE_PATH = "src/main/resources/files/categories.txt";
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(row -> {
                        String[] data = row.split("\\s+");
                        this.categoryRepository.saveAndFlush(new Category(data[0]));
                    });
        }
    }

    @Override
    public Set<Category> getRandomCategories() {
        List<Category> allCategories = categoryRepository.findAll();

        int categoryCount = allCategories.size();
        if (categoryCount == 0) {
            throw new IllegalStateException("No categories found in the database.");
        }

        int numberOfCategories = ThreadLocalRandom.current().nextInt(1, Math.min(categoryCount, 5) + 1);

        Set<Category> randomCategories = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(categoryCount))
                .distinct()
                .limit(numberOfCategories)
                .mapToObj(allCategories::get)
                .collect(Collectors.toSet());

        return randomCategories;
    }
}
