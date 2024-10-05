package lab.relations;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String name;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }
}
