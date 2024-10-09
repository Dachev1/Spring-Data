package org.example.bookshop_system.service;

import java.io.IOException;

public interface BookService {
    void seedBooks() throws IOException;

    void getAllBooksAfter2000year();

    void getAllBooksFromAuthorGeorgePowell();
}
