package exercise.ex06.entities.competitions;

import exercise.BaseEntity;
import exercise.ex06.entities.games.Game;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "competitions")
public class Competition extends BaseEntity {
    @Column
    private String name;

    @OneToMany(mappedBy = "competition")
    private Set<Game> games;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private CompetitionType type;
}
