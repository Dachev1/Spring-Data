package org.example.car_dealer.service.dtos.exportDtos.car;

import java.io.Serializable;

public class CarInfoDto implements Serializable {

    private String make;

    private String model;

    private double travelledDistance;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(double travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
