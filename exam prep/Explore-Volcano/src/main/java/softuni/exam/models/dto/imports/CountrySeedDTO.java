package softuni.exam.models.dto.imports;

import javax.validation.constraints.Size;

public class CountrySeedDTO {

    @Size(min = 3, max = 30)
    private String name;

    @Size(min = 3, max = 30)
    private String capital;

    public @Size(min = 3, max = 30) String getName() {
        return name;
    }

    public void setName(@Size(min = 3, max = 30) String name) {
        this.name = name;
    }

    public @Size(min = 3, max = 30) String getCapital() {
        return capital;
    }

    public void setCapital(@Size(min = 3, max = 30) String capital) {
        this.capital = capital;
    }
}
