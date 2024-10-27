package org.example.car_dealer.service.dtos.exportDtos.car;

import java.io.Serializable;

public class CarSaleDetailDto implements Serializable {
    private String make;
    private String model;
    private double totalPrice;

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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
