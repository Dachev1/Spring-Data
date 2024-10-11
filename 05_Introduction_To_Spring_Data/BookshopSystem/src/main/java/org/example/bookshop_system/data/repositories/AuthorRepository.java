package org.example.bookshop_system.data.repositories;

import org.example.bookshop_system.data.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAuthorsByBooksReleaseDateBefore(LocalDate localDate);


    @Query("SELECT a, COUNT(b) AS bookCount " +
            "FROM Author a LEFT JOIN a.books b " +
            "GROUP BY a " +
            "ORDER BY bookCount DESC")
    List<Object[]> findAllAuthorsWithBookCount();
}
