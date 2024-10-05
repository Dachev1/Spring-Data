package lab.relations.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trucks")
public class Truck extends Vehicle {
    private static final String TYPE = "truck";

    @Basic
    private Double loadCapacity;

    @ManyToMany
    @JoinTable(
            name = "trucks_drivers", // Join table name
            joinColumns = @JoinColumn(name = "truck_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id")
    )
    private List<Driver> drivers;

    public Truck() {
        this.drivers = new ArrayList<>();
    }

    public Truck(String model, BigDecimal price, String fuelType, Double loadCapacity) {
        super(TYPE, model, price, fuelType);
        this.loadCapacity = loadCapacity;
        this.drivers = new ArrayList<>();
    }

    public List<Driver> getDrivers() {
        return drivers;
    }
}
