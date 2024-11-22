package softuni.exam.models.dto.imports;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class VolcanoSeedDTO {

    @Size(min = 2, max = 30)
    @NotNull
    private String name;

    @Positive
    @NotNull
    private Integer elevation;

    @NotNull
    private String volcanoType;

    @NotNull
    private Boolean isActive;

    private LocalDate lastEruption;

    @NotNull
    private Integer country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public String getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(String volcanoType) {
        this.volcanoType = volcanoType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(LocalDate lastEruption) {
        this.lastEruption = lastEruption;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }
}
