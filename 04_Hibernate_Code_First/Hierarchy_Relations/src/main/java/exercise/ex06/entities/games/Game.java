package exercise.ex06.entities.games;

import exercise.BaseEntity;
import exercise.ex06.entities.competitions.Competition;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    @Column(name = "home_goals")
    private int homeGoals;

    @Column(name = "away_goals")
    private int awayGoals;

    @Column(name = "game_datetime", nullable = false)
    private LocalDateTime dateTimeOfGame;

    @Column(name = "home_win_bet_rate", nullable = false)
    private double homeTeamWinBetRate;

    @Column(name = "away_win_bet_rate", nullable = false)
    private double awayTeamWinBetRate;

    @Column(name = "draw_bet_rate", nullable = false)
    private double drawGameBetRate;

    @ManyToOne
    @JoinColumn(name = "round_id", referencedColumnName = "id", nullable = false)
    private Round round;

    @ManyToOne
    @JoinColumn(name = "competition_id", referencedColumnName = "id", nullable = false)
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id", nullable = false)
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id", nullable = false)
    private Team awayTeam;
}
