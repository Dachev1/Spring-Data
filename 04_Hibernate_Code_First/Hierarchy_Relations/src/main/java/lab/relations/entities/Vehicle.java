package lab.relations.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // This will generate a single table for the hierarchy
abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)  // This should be compatible with JOINED strategy
    private int id;

    @Basic
    private String type;

    @Basic
    private String model;

    @Basic
    private BigDecimal price;

    @Column(name = "fuel_type")
    private String fuelType;

    protected Vehicle() {
    }

    protected Vehicle(String type, String model, BigDecimal price, String fuelType) {
        this.type = type;
        this.model = model;
        this.price = price;
        this.fuelType = fuelType;
    }
}
