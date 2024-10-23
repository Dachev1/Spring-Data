package org.example.car_dealer.service.dtos.importDtos.supplier;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SupplierDto {
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    private boolean isImporter;

    public @NotNull @Size(min = 3, max = 100) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 3, max = 100) String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}
