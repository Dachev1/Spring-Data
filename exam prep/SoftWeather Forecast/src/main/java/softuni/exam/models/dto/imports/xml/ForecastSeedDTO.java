package softuni.exam.models.dto.imports.xml;

import softuni.exam.util.LocalTimeAdapter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.DayOfWeek;
import java.time.LocalTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastSeedDTO {

    @XmlElement(name = "day_of_week")
    @NotNull
    private DayOfWeek dayOfWeek;

    @XmlElement(name = "max_temperature")
    @NotNull
    @Min(-20)
    @Max(60)
    private Double maxTemperature;

    @XmlElement(name = "min_temperature")
    @NotNull
    @Min(-50)
    @Max(40)
    private Double minTemperature;

    @XmlElement(name = "sunrise")
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    @NotNull
    private LocalTime sunrise;

    @XmlElement(name = "sunset")
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    @NotNull
    private LocalTime sunset;

    @XmlElement(name = "city")
    @NotNull
    private Long city;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalTime sunset) {
        this.sunset = sunset;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }
}
