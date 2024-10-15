package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.repository.BookInfo;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        // From previous exercise
        // seedData();
        // printAllBooksAfterYear(2000);
        // printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        // printAllAuthorsAndNumberOfTheirBooks();
        // pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        // ex_01
        // printAllBooksTitleByAgeRestriction("miNor");
        // printAllBooksTitleByAgeRestriction("teEN");

        // ex_02
        //printAllBooksWithGoldenEditionWhichPageIsLessThan5000();

        // ex_03
        // printAllBookBetweenGivenPriceLimit(BigDecimal.valueOf(5), BigDecimal.valueOf(40));

        // ex_04
        // this.bookService.findAllBookThatIsNotReleasedInGivenYear(2000);
        // this.bookService.findAllBookThatIsNotReleasedInGivenYear(1998);

        // ex_05
        // this.bookService.findAllBooksByGivenReleaseDateBefore(LocalDate.of(1992, 4, 12)).forEach(System.out::println);

        // ex_06
        // this.authorService.getAllAuthorsByFirstNameWhoEndsWithGivenLetter("dy").forEach(System.out::println);

        // ex_07
        // this.bookService.findAllBooksContainsGivenStringInTheTitle("WOR").forEach(System.out::println);

        // ex_08
        // this.bookService.findAllBooksByAuthorLastNamePrefix("gr").forEach(System.out::println);

        // ex_09
        // this.bookService.getCountBooksByTitleLengthGreaterThan(12);
        // this.bookService.getCountBooksByTitleLengthGreaterThan(40);

        // ex_10
        // this.bookService.printTotalBookCopiesByAuthorNames();

        // ex_11
        // BookInfo bookInfo = this.bookService.findInfoByTitle("Things Fall Apart");
        // System.out.println(bookInfo.getEditionType());

        // ex_12
        // this.bookService.increaseTheCopiesForTheBooksAfterGivenReleaseDate("12 Oct 2005", 1000);

        // ex_13
        // System.out.println(this.bookService.deleteBooksWithCopiesLessThan(3700));

        // ex_14
       //printTotalBooksByAuthor("Christina", "Jordan");
    }

    private void printTotalBooksByAuthor(String firstName, String lastName) {
        int totalBooks = bookService.getTotalBooksByAuthor(firstName, lastName);
        System.out.printf("Total books by %s %s: %d%n", firstName, lastName, totalBooks);
    }

    private void printAllBookBetweenGivenPriceLimit(BigDecimal lowerLimit, BigDecimal upperLimit) {
        this.bookService.findAllBookByPriceBetween(lowerLimit, upperLimit).forEach(System.out::println);

    }

    private void printAllBooksWithGoldenEditionWhichPageIsLessThan5000() {
        this.bookService.findAllBooksWithGoldenEditionWhichPageIsLessThan5000().forEach(System.out::println);
    }

    private void printAllBooksTitleByAgeRestriction(String restriction) {
        this.bookService.findAllBooksByAgeRestriction(restriction).forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        this.bookService.findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName).forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        this.authorService.getAllAuthorsOrderByCountOfTheirBooks().forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        this.bookService.findAllAuthorsWithBooksWithReleaseDateBeforeYear(year).forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        this.bookService.findAllBooksAfterYear(year).stream().map(Book::getTitle).forEach(System.out::println);
    }

    private void seedData() throws IOException {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
