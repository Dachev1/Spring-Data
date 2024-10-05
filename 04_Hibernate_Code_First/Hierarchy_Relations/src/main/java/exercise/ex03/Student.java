package exercise.ex03;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends BaseInformation{
    @Column(name = "average_grade")
    private double averageGrade;

    @Column
    private int attendance;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();
}
