package com.example.springintro.repository;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.enums.AgeRestriction;
import com.example.springintro.model.entity.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findByPriceLessThanOrPriceGreaterThan(BigDecimal lowerLimit, BigDecimal upperLimit);

    List<Book> findAllByTitleContaining(String str);

    List<Book> findAllByAuthorLastNameStartingWith(String str);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :length")
    long countOfBooksByTitleLengthGreaterThan(@Param("length") int length);

    @Query("SELECT b.author.firstName, b.author.lastName, SUM(b.copies) as totalCopies " +
            "FROM Book b " +
            "GROUP BY b.author.firstName, b.author.lastName " +
            "ORDER BY totalCopies DESC")
    List<Object[]> findTotalBookCopiesByAuthorNames();

    List<Book> findAllBookByCopiesLessThan(int copies);

    @Procedure(procedureName = "get_books_by_author")
    int getTotalBooksByAuthor(String firstName, String lastName);
}

