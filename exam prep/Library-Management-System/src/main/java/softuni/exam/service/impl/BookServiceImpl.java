package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.BookSeedDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.enums.Genre;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class BookServiceImpl implements BookService {
    private final static String FILE_PATH = "src/main/resources/files/json/books.json";

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder result = new StringBuilder();

        BookSeedDTO[] bookSeedDTOS = gson.fromJson(readBooksFromFile(), BookSeedDTO[].class);

        for (BookSeedDTO bookDTO : bookSeedDTOS) {
            if (!validationUtil.isValid(bookDTO) || bookRepository.existsByTitle(bookDTO.getTitle())) {
                result.append("Invalid book").append(System.lineSeparator());
                continue;
            }

            Book book = modelMapper.map(bookDTO, Book.class);
            book.setGenre(Genre.valueOf(bookDTO.getGenre()));

            bookRepository.save(book);

            result.append(String.format("Successfully imported book %s - %s",
                            book.getAuthor(), book.getTitle()))
                    .append(System.lineSeparator());
        }

        return result.toString().trim();
    }
}
