package org.example.car_dealer.service.dtos.importDtos.part;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class PartDto implements Serializable {
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @Positive
    private double price;

    @NotNull
    @PositiveOrZero
    private int quantity;

    public @NotNull @Size(min = 3, max = 100) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 3, max = 100) String name) {
        this.name = name;
    }

    @NotNull
    @Positive
    public double getPrice() {
        return price;
    }

    public void setPrice(@NotNull @Positive double price) {
        this.price = price;
    }

    @NotNull
    @PositiveOrZero
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull @PositiveOrZero int quantity) {
        this.quantity = quantity;
    }
}
