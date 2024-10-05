package exercise.ex04;

import exercise.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity {
    @Column
    private String name;

    @OneToMany(mappedBy = "medicament")
    private Set<Visitation> visitations;

    @ManyToMany(mappedBy = "medicaments")
    private Set<Diagnose> diagnoses;
}
