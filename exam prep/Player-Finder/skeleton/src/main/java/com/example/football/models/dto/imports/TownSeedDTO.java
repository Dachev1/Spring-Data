package com.example.football.models.dto.imports;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TownSeedDTO {
    @NotNull
    @Size(min = 2)
    private String name;

    @Min(1)
    private int population;

    @NotNull
    @Size(min = 10)
    private String travelGuide;

    public TownSeedDTO() {}

    public @NotNull @Size(min = 2) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2) String name) {
        this.name = name;
    }

    @Min(1)
    public int getPopulation() {
        return population;
    }

    public void setPopulation(@Min(1) int population) {
        this.population = population;
    }

    public @NotNull @Size(min = 10) String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(@NotNull @Size(min = 10) String travelGuide) {
        this.travelGuide = travelGuide;
    }
}