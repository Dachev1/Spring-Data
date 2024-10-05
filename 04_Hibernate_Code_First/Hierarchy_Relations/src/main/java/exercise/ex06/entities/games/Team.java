package exercise.ex06.entities.games;

import exercise.BaseEntity;
import exercise.ex06.entities.geography.Town;
import exercise.ex06.enums.Initials;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    @Column
    private String name;

    @Column
    private byte[] logo;

    @Column(name = "initials")
    @Enumerated(EnumType.STRING)
    private Initials initials;

    @Column
    private double budget;

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    @OneToOne()
    @JoinColumn(name = "primary_kit_color", referencedColumnName = "id")
    private Color primaryKitColor;

    @OneToOne()
    @JoinColumn(name = "secondary_kit_color", referencedColumnName = "id")
    private Color secondaryKitColor;

    @OneToMany(mappedBy = "team")
    private List<Player> players;
}
