package org.example.bookshop_system.service.impl;

import org.example.bookshop_system.data.entities.Author;
import org.example.bookshop_system.data.entities.Book;
import org.example.bookshop_system.data.repositories.AuthorRepository;
import org.example.bookshop_system.service.AuthorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String FILE_PATH = "src/main/resources/files/authors.txt";
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() == 0) {
            Files.readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(row -> !row.isEmpty())
                    .forEach(row -> {
                        String[] data = row.split("\\s+");
                        this.authorRepository.saveAndFlush(new Author(data[0], data[1]));
                    });
        }
    }

    @Override
    public Author getRandomAuthor() {
        long count = this.authorRepository.count();  // Get the total number of authors

        if (count == 0) {
            throw new IllegalStateException("No authors found in the database.");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt((int) count);

        return this.authorRepository.findAll(PageRequest.of(randomIndex, 1)).getContent().get(0);
    }

    @Override
    public void authorsWithBooksBefore1990() {
        this.authorRepository.findAuthorsByBooksReleaseDateBefore(LocalDate.parse("1990-01-01"))
                .forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));
    }

    @Override
    public void allAuthorsOrderByNumberOfBooksInDescOrder() {
        List<Object[]> authorsWithCounts = authorRepository.findAllAuthorsWithBookCount();

        for (Object[] result : authorsWithCounts) {
            Author author = (Author) result[0];
            Long bookCount = (Long) result[1];

            System.out.printf("Author: %s %s, Books: %d%n", author.getFirstName(), author.getLastName(), bookCount);
        }
    }
}
