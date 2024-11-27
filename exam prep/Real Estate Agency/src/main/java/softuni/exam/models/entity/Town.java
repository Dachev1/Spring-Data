package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Town extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String townName;

    @Column(nullable = false)
    private int population;

    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL)
    private Set<Agent> agents;

    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL)
    private Set<Apartment> apartments;

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Set<Agent> getAgents() {
        return agents;
    }

    public void setAgents(Set<Agent> agents) {
        this.agents = agents;
    }

    public Set<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }
}
