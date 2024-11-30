package softuni.exam.models.dto.imports;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CountrySeedDTO {

    @Size(min = 3, max = 40)
    private String name;

    @Positive
    private Double area;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getArea() {
        return this.area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}
