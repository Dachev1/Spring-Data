package exercise.ex06.entities.geography;

import exercise.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "continents")
public class Continent extends BaseEntity {
    @Column
    private String name;

    @ManyToMany(mappedBy = "continents")
    private List<Country> countries;
}
