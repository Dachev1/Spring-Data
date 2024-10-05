package exercise.ex06.entities.games;

import exercise.ex06.enums.Positions;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @Column(length = 2)
    @Enumerated(EnumType.ORDINAL)
    private Positions id;

    @Column
    private String position_description;

    @OneToMany(mappedBy = "position")
    private List<Player> players;
}
