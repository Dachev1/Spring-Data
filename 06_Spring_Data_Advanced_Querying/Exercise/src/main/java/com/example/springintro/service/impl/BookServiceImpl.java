package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.model.entity.enums.AgeRestriction;
import com.example.springintro.model.entity.enums.EditionType;
import com.example.springintro.repository.BookInfo;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

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
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAgeRestriction(String ageRestrictionType) {
        AgeRestriction ageRestrictionEnum = AgeRestriction.valueOf(ageRestrictionType.toUpperCase());

        return this.bookRepository.findAllByAgeRestriction(ageRestrictionEnum)
                .stream()
                .map(Book::getTitle)
                .toList();

    }

    @Override
    public List<String> findAllBooksWithGoldenEditionWhichPageIsLessThan5000() {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<String> findAllBookByPriceBetween(BigDecimal lowerLimit, BigDecimal upperLimit) {
        return this.bookRepository.findByPriceLessThanOrPriceGreaterThan(lowerLimit, upperLimit)
                .stream()
                .map(book -> book.getTitle() + " -> $" + book.getPrice())
                .collect(Collectors.toList());
    }

    @Override
    public void findAllBookThatIsNotReleasedInGivenYear(int year) {
        this.bookRepository.findAll()
                .stream()
                .filter(book -> book.getReleaseDate().getYear() != year)
                .forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public List<String> findAllBooksByGivenReleaseDateBefore(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateBefore(date)
                .stream()
                .map(book -> String.format("%s %s %.2f", book.getTitle(), book.getEditionType(), book.getPrice()))
                .toList();
    }

    @Override
    public List<String> findAllBooksContainsGivenStringInTheTitle(String str) {
        return this.bookRepository.findAllByTitleContaining(str)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<String> findAllBooksByAuthorLastNamePrefix(String prefix) {
        return this.bookRepository.findAllByAuthorLastNameStartingWith(prefix)
                .stream()
                .map(book -> String.format("%s (%s %s)", book.getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .toList();
    }

    @Override
    public void getCountBooksByTitleLengthGreaterThan(int length) {
        System.out.println(this.bookRepository.countOfBooksByTitleLengthGreaterThan(length));
    }

    @Override
    public void printTotalBookCopiesByAuthorNames() {
        List<Object[]> results = bookRepository.findTotalBookCopiesByAuthorName();

        results.forEach(result -> {
            String firstName = (String) result[0];
            String lastName = (String) result[1];
            Long totalCopies = (Long) result[2];

            System.out.println("Author: " + firstName + " " + lastName + ", Total Copies: " + totalCopies);
        });
    }

    @Override
    public void increaseTheCopiesForTheBooksAfterGivenReleaseDate(String dateStr, int copiesToAdd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);

        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(localDate);

        books.forEach(book ->{
            book.setCopies(book.getCopies() + copiesToAdd);
            this.bookRepository.save(book);
        });
    }

    @Override
    public int deleteBooksWithCopiesLessThan(int copiesCount) {
        List<Book> books = this.bookRepository.findAllBookByCopiesLessThan(copiesCount);
        this.bookRepository.deleteAll(books);
        return books.size();
    }

    @Override
    public int getTotalBooksByAuthor(String firstName, String lastName) {
        return bookRepository.getTotalBooksByAuthor(firstName, lastName);
    }

    @Override
    public BookInfo findInfoByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
