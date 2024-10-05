package lab.relations.entities;

import jakarta.persistence.*;
import lab.relations.PlateNumber;

import java.math.BigDecimal;

@Entity
@Table(name = "cars")
public class Car extends Vehicle {
    private static final String TYPE = "car";

    @Basic
    private int seats;

    @OneToOne
    @JoinColumn(name = "plate_number_id", referencedColumnName = "id")
    private PlateNumber plateNumber;

    public Car() {}

    public Car(String model, BigDecimal price, String fuelType, int seats, PlateNumber plateNumber) {
        super(TYPE, model, price, fuelType);
        this.seats = seats;
        this.plateNumber = plateNumber;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public PlateNumber getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(PlateNumber plateNumber) {
        this.plateNumber = plateNumber;
    }
}
