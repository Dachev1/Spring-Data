package softuni.exam.models.dto.imports;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ConstellationSeedDTO {
    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 5)
    private String description;

    public @Size(min = 3, max = 20) String getName() {
        return name;
    }

    public void setName(@Size(min = 3, max = 20) String name) {
        this.name = name;
    }

    public @Size(min = 5) String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 5) String description) {
        this.description = description;
    }
}
