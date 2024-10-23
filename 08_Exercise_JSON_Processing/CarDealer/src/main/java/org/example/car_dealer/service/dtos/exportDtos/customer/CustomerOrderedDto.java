package org.example.car_dealer.service.dtos.exportDtos.customer;

import org.example.car_dealer.service.dtos.exportDtos.sale.SaleDto;

import java.util.List;

public class CustomerOrderedDto {
    private Long id;
    private String name;
    private String birthDate;
    private boolean isYoungDriver;
    private List<SaleDto> sales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public List<SaleDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleDto> sales) {
        this.sales = sales;
    }
}
