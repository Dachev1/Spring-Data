package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Basic
    private String capital;

    @OneToMany(mappedBy = "country")
    private Set<Volcano> volcanoes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Set<Volcano> getVolcanoes() {
        return volcanoes;
    }

    public void setVolcanoes(Set<Volcano> volcanoes) {
        this.volcanoes = volcanoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.name) && Objects.equals(capital, country.capital) && Objects.equals(volcanoes, country.volcanoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capital, volcanoes);
    }
}
