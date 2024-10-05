package exercise.ex06.entities.bet;

import exercise.BaseEntity;
import exercise.ex06.entities.users.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bets")
public class Bet extends BaseEntity {
    @Column(name = "bet_money", nullable = false)
    private BigDecimal betMoney;

    @Column(name = "date_time", nullable = false)
    private Date dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
