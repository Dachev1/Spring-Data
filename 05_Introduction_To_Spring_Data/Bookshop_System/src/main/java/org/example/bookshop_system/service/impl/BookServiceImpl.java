package org.example.bookshop_system.service.impl;

import org.example.bookshop_system.data.entities.Author;
import org.example.bookshop_system.data.entities.Book;
import org.example.bookshop_system.data.entities.Category;
import org.example.bookshop_system.data.entities.enums.AgeRestrictionType;
import org.example.bookshop_system.data.entities.enums.EditionType;
import org.example.bookshop_system.data.repositories.BookRepository;
import org.example.bookshop_system.service.AuthorService;
import org.example.bookshop_system.service.BookService;
import org.example.bookshop_system.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private static final String FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .forEach(row -> {
                        String[] data = row.split("\\s+");

                        Author author = authorService.getRandomAuthor();
                        EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                        LocalDate releaseDate = LocalDate.parse(data[1],
                                DateTimeFormatter.ofPattern("d/M/yyyy"));
                        int copies = Integer.parseInt(data[2]);
                        BigDecimal price = new BigDecimal(data[3]);
                        AgeRestrictionType ageRestriction = AgeRestrictionType
                                .values()[Integer.parseInt(data[4])];
                        String title = Arrays.stream(data)
                                .skip(5)
                                .collect(Collectors.joining(" "));
                        Set<Category> categories = categoryService.getRandomCategories();


                        Book book = new Book(title, editionType, price, releaseDate,
                                ageRestriction, author, categories, copies);

                        bookRepository.saveAndFlush(book);
                    });
        }
    }

    @Override
    public void getAllBooksAfter2000year() {
        this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"))
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    @Override
    public void getAllBooksFromAuthorGeorgePowell() {
        this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc("George", "Powell")
                .forEach(book -> System.out.println(book.getTitle()));
    }
}
