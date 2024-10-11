package org.example.bookshop_system.data.entities;

import org.example.bookshop_system.data.entities.base_entities.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    public Author() {}

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
