package org.example.bookshop_system.service;

import org.example.bookshop_system.data.entities.Author;

import java.io.IOException;
import java.util.Set;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    void authorsWithBooksBefore1990();

    void allAuthorsOrderByNumberOfBooksInDescOrder();
}
