package org.example.car_dealer.service.dtos.exportDtos.customer;

import java.io.Serializable;

public class CustomerTotalSalesDto implements Serializable {
    private String name;
    private long boughtCars;
    private double spentMoney;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(long boughtCars) {
        this.boughtCars = boughtCars;
    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(double spentMoney) {
        this.spentMoney = spentMoney;
    }
}
