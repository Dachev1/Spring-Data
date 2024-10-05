package exercise.ex06.entities.games;

import exercise.BaseEntity;
import exercise.ex06.entities.statistics.PlayerStatistic;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {
    @Column
    private String name;

    @Column(name = "squad_number")
    private String squadNumber;

    @Column(name = "is_currently_injured")
    private boolean isCurrentlyInjured;

    @OneToMany(mappedBy = "player")
    private Set<PlayerStatistic> playerStatistics;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;
}
