package org.example.bookshop_system;

import org.example.bookshop_system.data.repositories.AuthorRepository;
import org.example.bookshop_system.service.AuthorService;
import org.example.bookshop_system.service.BookService;
import org.example.bookshop_system.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    public CommandLineRunnerImpl(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.bookService.getAllBooksFromAuthorGeorgePowell();
    }

    private void seedDatabase() throws IOException {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
    }
}
