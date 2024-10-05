package exercise.ex06.entities.competitions;


import exercise.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_type")
public class CompetitionType extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
}
