package lab.relations.entities;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Bike extends Vehicle{
    private static final String TYPE = "bike";
    public Bike() {}

    public Bike(String model, BigDecimal price, String fuelType) {
        super(TYPE, model, price, fuelType);
    }
}
