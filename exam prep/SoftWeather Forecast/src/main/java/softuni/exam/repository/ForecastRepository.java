package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.exports.ForecastDTO;
import softuni.exam.models.entity.Forecast;

import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    @Query("SELECT new softuni.exam.models.dto.exports.ForecastDTO(f.city.cityName, f.minTemperature, f.maxTemperature, f.sunrise, f.sunset) " +
            "FROM Forecast f " +
            "WHERE f.dayOfWeek = 'SUNDAY' AND f.city.population < 150000 " +
            "ORDER BY f.maxTemperature DESC, f.id ASC")
    List<ForecastDTO> findSundayForecasts();
}
