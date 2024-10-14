package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import org.springframework.data.jpa.repository.Modifying;

import java.beans.Transient;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBooksByAgeRestriction(String ageRestrictionType);

    List<String> findAllBooksWithGoldenEditionWhichPageIsLessThan5000();

    List<String> findAllBookByPriceBetween(BigDecimal lowerLimit, BigDecimal upperLimit);

    void findAllBookThatIsNotReleasedInGivenYear(int year);

    List<String> findAllBooksByGivenReleaseDateBefore(LocalDate date);

    List<String> findAllBooksContainsGivenStringInTheTitle(String str);

    List<String> findAllBooksByAuthorLastNamePrefix(String prefix);

    void getCountBooksByTitleLengthGreaterThan(int length);

    void printTotalBookCopiesByAuthorNames();

    void increaseTheCopiesForTheBooksAfterGivenReleaseDate(String dateStr, int copiesToAdd);

    int deleteBooksWithCopiesLessThan(int copiesCount);

    int getTotalBooksByAuthor(String firstName, String lastName);
}
