package org.example.car_dealer.service.dtos.exportDtos.sale;

import org.example.car_dealer.service.dtos.exportDtos.car.CarSaleDetailDto;

public class SaleDto {
    private Double discount;
    private CarSaleDetailDto car;

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public CarSaleDetailDto getCar() {
        return car;
    }

    public void setCar(CarSaleDetailDto car) {
        this.car = car;
    }
}