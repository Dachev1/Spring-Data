package exercise.ex06.entities.geography;

import exercise.BaseEntity;
import exercise.ex06.entities.games.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @OneToMany(mappedBy = "town")
    private List<Team> teams = new ArrayList<>();
}
