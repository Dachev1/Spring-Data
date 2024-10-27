package org.example.car_dealer.service.dtos.exportDtos.supplier;

import java.io.Serializable;

public class SupplierDto implements Serializable {
    private long id;
    private String name;
    private long partsCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(long partsCount) {
        this.partsCount = partsCount;
    }
}
