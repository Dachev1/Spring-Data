package lab.relations.entities;

import jakarta.persistence.*;
import lab.relations.Company;

import java.math.BigDecimal;
@Entity
@Table(name = "planes")
public class Plane extends Vehicle{
    private static final String TYPE = "plane";
    @Basic
    private int passengerCapacity;
    @Basic
    private String airline;

    @ManyToOne(cascade = CascadeType.PERSIST) // Cascade the persist operation
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public Plane() {}

    public Plane(String model, BigDecimal price, String fuelType, int passengerCapacity, String airline, Company company) {
        super(TYPE, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
        this.airline = airline;
        this.company = company;
    }
}
