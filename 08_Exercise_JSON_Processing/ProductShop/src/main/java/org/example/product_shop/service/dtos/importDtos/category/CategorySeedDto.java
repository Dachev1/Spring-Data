package org.example.product_shop.service.dtos.importDtos.category;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;

public class CategorySeedDto {
    @Expose
    @NotNull
    private String name;

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }
}
