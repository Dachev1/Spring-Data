package softuni.exam.models.dto.imports;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class StarSeedDTO {

    @Size(min = 6)
    private String description;

    @Positive
    private double lightYears;

    @Size(min = 2, max = 30)
    private String name;

    private String starType;

    private long constellation;

    public @Size(min = 6) String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 6) String description) {
        this.description = description;
    }

    @Positive
    public double getLightYears() {
        return lightYears;
    }

    public void setLightYears(@Positive double lightYears) {
        this.lightYears = lightYears;
    }

    public @Size(min = 2, max = 30) String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 30) String name) {
        this.name = name;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }

    public long getConstellation() {
        return constellation;
    }

    public void setConstellation(long constellation) {
        this.constellation = constellation;
    }
}
