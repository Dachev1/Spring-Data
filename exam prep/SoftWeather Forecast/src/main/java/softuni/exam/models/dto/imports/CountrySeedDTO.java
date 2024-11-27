package softuni.exam.models.dto.imports;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CountrySeedDTO implements Serializable {

    @Expose
    @NotBlank
    @Size(min = 2, max = 60)
    private String countryName;

    @Expose
    @NotBlank
    @Size(min = 2, max = 20)
    private String currency;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
