package exercise.ex06.entities.statistics;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlayerStatisticsId implements Serializable {

    private Long gameId;
    private Long playerId;

    public PlayerStatisticsId() {
    }

    public PlayerStatisticsId(Long gameId, Long playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }
}
