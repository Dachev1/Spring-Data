package org.example.car_dealer.service.dtos.importDtos.car;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CarDto implements Serializable {
    @NotNull
    @Size(min = 3, max = 50)
    private String make;

    @NotNull
    @Size(min = 3, max = 50)
    private String model;

    @NotNull
    @Positive
    private double travelledDistance;

    public @NotNull @Size(min = 3, max = 50) String getMake() {
        return make;
    }

    public void setMake(@NotNull @Size(min = 3, max = 50) String make) {
        this.make = make;
    }

    public @NotNull @Size(min = 3, max = 50) String getModel() {
        return model;
    }

    public void setModel(@NotNull @Size(min = 3, max = 50) String model) {
        this.model = model;
    }

    @NotNull
    @Positive
    public double getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(@NotNull @Positive double travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
