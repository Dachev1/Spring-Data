package softuni.exam.models.dto.imports;

import javax.validation.constraints.Size;

public class CountrySeedDTO {

    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 2, max = 19)
    private String countryCode;

    @Size(min = 2, max = 19)
    private String currency;

    public @Size(min = 2, max = 30) String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 30) String name) {
        this.name = name;
    }

    public @Size(min = 2, max = 19) String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(@Size(min = 2, max = 19) String countryCode) {
        this.countryCode = countryCode;
    }

    public @Size(min = 2, max = 19) String getCurrency() {
        return currency;
    }

    public void setCurrency(@Size(min = 2, max = 19) String currency) {
        this.currency = currency;
    }
}
