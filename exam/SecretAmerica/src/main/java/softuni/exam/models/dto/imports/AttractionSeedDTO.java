package softuni.exam.models.dto.imports;

import jakarta.validation.constraints.*;

public class AttractionSeedDTO {

    @Size(min = 5, max = 40)
    @NotNull
    private String name;

    @Size(min = 10, max = 100)
    @NotNull
    private String description;

    @Size(min = 3, max = 30)
    @NotNull
    private String type;

    @Min(0)
    @NotNull
    private Integer elevation;

    @NotNull
    private Long country;

    public AttractionSeedDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }
}
