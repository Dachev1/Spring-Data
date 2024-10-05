package exercise.ex06.entities.competitions;

import exercise.BaseEntity;
import exercise.ex06.enums.PredictionTypes;
import jakarta.persistence.*;

@Entity
@Table(name = "result_prediction")
public class ResultPrediction extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "prediction", nullable = false)
    private PredictionTypes prediction;
}