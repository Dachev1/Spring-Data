package exercise.ex06.entities.geography;

import exercise.ex06.enums.IdLetters;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @Column(length = 3)
    @Enumerated(EnumType.ORDINAL)
    private IdLetters id;

    @Column
    private String name;

    @OneToMany(mappedBy = "country")
    private List<Town> towns;

    @ManyToMany
    @JoinTable(
            name = "countries_continents",
            joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "continent_id", referencedColumnName = "id")
    )
    private List<Continent> continents;
}
