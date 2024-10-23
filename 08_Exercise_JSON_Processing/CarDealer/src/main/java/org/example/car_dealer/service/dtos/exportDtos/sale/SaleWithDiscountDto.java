package org.example.car_dealer.service.dtos.exportDtos.sale;

import org.example.car_dealer.service.dtos.exportDtos.car.CarInfoDto;

public class SaleWithDiscountDto {
    private CarInfoDto car;
    private String customerName;
    private double discount;
    private double price;
    private double priceWithDiscount;

    public CarInfoDto getCar() {
        return car;
    }

    public void setCar(CarInfoDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
