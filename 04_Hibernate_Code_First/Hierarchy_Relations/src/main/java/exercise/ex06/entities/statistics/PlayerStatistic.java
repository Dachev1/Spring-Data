package exercise.ex06.entities.statistics;
import exercise.ex06.entities.games.Game;
import exercise.ex06.entities.games.Player;
import jakarta.persistence.*;

@Entity
@Table(name = "player_statistics")
public class PlayerStatistic {

    @EmbeddedId
    private PlayerStatisticsId id;

    @Column(name = "scored_goals", nullable = false)
    private int scoredGoals;

    @Column(name = "player_assists", nullable = false)
    private int playerAssists;

    @Column(name = "played_minutes", nullable = false)
    private int playedMinutes;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    private Player player;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false)
    private Game game;
}
