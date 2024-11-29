package com.example.football.models.dto.imports;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeamSeedDTO {
    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Size(min = 3)
    private String stadiumName;

    @Min(1000)
    private int fanBase;

    @NotNull
    @Size(min = 10)
    private String history;

    @NotNull
    private String townName;

    public TeamSeedDTO() {}

    public @NotNull @Size(min = 3) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 3) String name) {
        this.name = name;
    }

    public @NotNull @Size(min = 3) String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(@NotNull @Size(min = 3) String stadiumName) {
        this.stadiumName = stadiumName;
    }

    @Min(1000)
    public int getFanBase() {
        return fanBase;
    }

    public void setFanBase(@Min(1000) int fanBase) {
        this.fanBase = fanBase;
    }

    public @NotNull @Size(min = 10) String getHistory() {
        return history;
    }

    public void setHistory(@NotNull @Size(min = 10) String history) {
        this.history = history;
    }

    public @NotNull String getTownName() {
        return townName;
    }

    public void setTownName(@NotNull String townName) {
        this.townName = townName;
    }
}
