package softuni.exam.models.dto.exports;

import java.time.LocalTime;

public class ForecastDTO {
    private String cityName;
    private double minTemperature;
    private double maxTemperature;
    private LocalTime sunrise;
    private LocalTime sunset;

    public ForecastDTO(String cityName, double minTemperature, double maxTemperature, LocalTime sunrise, LocalTime sunset) {
        this.cityName = cityName;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
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

    @Override
    public String toString() {
        return String.format("City: %s:\n\t-min temperature: %.2f\n\t--max temperature: %.2f\n\t---sunrise: %s\n\t----sunset: %s",
                cityName, minTemperature, maxTemperature, sunrise, sunset);
    }
}
