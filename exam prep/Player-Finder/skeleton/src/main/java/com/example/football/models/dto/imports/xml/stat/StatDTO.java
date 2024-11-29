package com.example.football.models.dto.imports.xml.stat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class StatDTO {

    @NotNull
    @Min(0)
    private Float passing;

    @NotNull
    @Min(0)
    private Float shooting;

    @NotNull
    @Min(0)
    private Float endurance;

    public Float getPassing() {
        return passing;
    }

    public void setPassing(Float passing) {
        this.passing = passing;
    }

    public Float getShooting() {
        return shooting;
    }

    public void setShooting(Float shooting) {
        this.shooting = shooting;
    }

    public Float getEndurance() {
        return endurance;
    }

    public void setEndurance(Float endurance) {
        this.endurance = endurance;
    }
}
