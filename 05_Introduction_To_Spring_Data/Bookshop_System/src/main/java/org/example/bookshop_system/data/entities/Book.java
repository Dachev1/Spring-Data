package org.example.bookshop_system.data.entities;

import org.example.bookshop_system.data.entities.base_entities.BaseEntity;
import org.example.bookshop_system.data.entities.enums.AgeRestrictionType;
import org.example.bookshop_system.data.entities.enums.EditionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "edition_type", nullable = false)
    @Enumerated
    private EditionType editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(nullable = false)
    @Enumerated
    private AgeRestrictionType restriction;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToMany
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories;

    public Book() {
    }

    public Book(String title, EditionType editionType, BigDecimal price, LocalDate releaseDate, AgeRestrictionType ageRestriction, Author author, Set<Category> categories, int copies) {
        this.title = title;
        this.editionType = editionType;
        this.price = price;
        this.releaseDate = releaseDate;
        this.restriction = ageRestriction;
        this.author = author;
        this.categories = categories;
        this.copies = copies;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getCopies() {
        return copies;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public AgeRestrictionType getRestriction() {
        return restriction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return copies == book.copies && Objects.equals(title, book.title) && Objects.equals(description, book.description) && editionType == book.editionType && Objects.equals(price, book.price) && Objects.equals(releaseDate, book.releaseDate) && restriction == book.restriction && Objects.equals(author, book.author) && Objects.equals(categories, book.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, editionType, price, copies, releaseDate, restriction, author, categories);
    }
}
