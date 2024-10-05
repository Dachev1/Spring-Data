package exercise.ex04;

import exercise.BaseEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {

    @Column
    private Date date;

    @Column
    private String comments;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    private Diagnose diagnose;

    @ManyToOne
    @JoinColumn(name = "medicament_id", referencedColumnName = "id")
    private Medicament medicament;
}
