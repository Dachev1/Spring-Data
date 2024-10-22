package org.example.product_shop.service.dtos.importDtos.product;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductSeedDto {
    @Expose
    @NotNull
    @Size(min = 3)
    private String name;

    @Expose
    @NotNull
    @Min(1)
    private BigDecimal price;


    public @NotNull @Size(min = 3) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 3) String name) {
        this.name = name;
    }

    public @NotNull @Min(1) BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull @Min(1) BigDecimal price) {
        this.price = price;
    }

}
