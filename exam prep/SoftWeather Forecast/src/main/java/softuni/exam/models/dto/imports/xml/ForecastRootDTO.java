package softuni.exam.models.dto.imports.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastRootDTO {

    @XmlElement(name = "forecast")
    private List<ForecastSeedDTO> forecasts;

    // Getters and setters
    public List<ForecastSeedDTO> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastSeedDTO> forecasts) {
        this.forecasts = forecasts;
    }
}
