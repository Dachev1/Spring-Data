package org.example.car_dealer.data.entities;

import jakarta.persistence.*;
import org.example.car_dealer.data.entities.base.BaseEntity;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    @Column(nullable = false)
    private double discount;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public double calculateDiscount() {
        if (customer.isYoungDriver()) {
            return discount + 5;
        }
        return discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
