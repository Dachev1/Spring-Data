package org.example.car_dealer.service.dtos.exportDtos.car;

import org.example.car_dealer.service.dtos.exportDtos.part.PartForCarsDto;

import java.io.Serializable;
import java.util.List;

public class CarWithPartsDto implements Serializable {
    private String make;
    private String model;
    private double travelledDistance;
    private List<PartForCarsDto> parts;

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

    public List<PartForCarsDto> getParts() {
        return parts;
    }

    public void setParts(List<PartForCarsDto> parts) {
        this.parts = parts;
    }
}
