package exercise.ex06.entities.bet;

import exercise.ex06.entities.games.Game;
import exercise.ex06.entities.competitions.ResultPrediction;
import jakarta.persistence.*;

@Entity
@Table(name = "bet_game")
public class BetGame {

    @EmbeddedId
    private BetGameId id;


    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false)
    private Game game;

    @ManyToOne
    @MapsId("betId")
    @JoinColumn(name = "bet_id", referencedColumnName = "id", nullable = false)
    private Bet bet;

    @OneToOne
    @JoinColumn(name = "result_prediction", referencedColumnName = "id")
    private ResultPrediction resultPrediction;
}
