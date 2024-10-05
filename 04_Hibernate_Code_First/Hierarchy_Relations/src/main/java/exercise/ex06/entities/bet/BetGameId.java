package exercise.ex06.entities.bet;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BetGameId implements Serializable {

    private Long gameId;
    private Long betId;

    public BetGameId() {}

    public BetGameId(Long gameId, Long betId) {
        this.gameId = gameId;
        this.betId = betId;
    }
}
