package softuni.exam.models.dto.imports;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TownSeedDTO {
    @NotNull
    @Size(min = 2)
    private String townName;

    @NotNull
    @Min(1)
    private int population;

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
