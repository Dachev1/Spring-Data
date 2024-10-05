package lab.relations.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    private String name;

    @ManyToMany(mappedBy = "drivers")
    private List<Truck> trucks;

    public Driver() {
        this.trucks = new ArrayList<>();
    }

    public Driver(String name) {
        this();
        this.name = name;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }
}
