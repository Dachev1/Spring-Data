package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.exports.ForecastDTO;
import softuni.exam.models.dto.imports.xml.ForecastRootDTO;
import softuni.exam.models.dto.imports.xml.ForecastSeedDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.validation.ValidationUtil;
import softuni.exam.util.xmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
public class ForecastServiceImpl implements ForecastService {
    private static final String FILE_PATH = "src/main/resources/files/xml/forecasts.xml";

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importForecasts() throws JAXBException, IOException {
        StringBuilder result = new StringBuilder();

        ForecastRootDTO forecastRootDTO = this.xmlParser.fromFile(this.FILE_PATH, ForecastRootDTO.class);

        for (ForecastSeedDTO dto : forecastRootDTO.getForecasts()) {
            // Validate DTO and check dayOfWeek
            if (!this.validationUtil.isValid(dto) || dto.getDayOfWeek() == null) {
                result.append("Invalid forecast - day of week is missing or invalid").append(System.lineSeparator());
                continue;
            }

            // Check if city exists
            Optional<City> optionalCity = this.cityRepository.findById(dto.getCity());
            if (optionalCity.isEmpty()) {
                result.append(String.format("Invalid forecast - city with ID %d not found%n", dto.getCity()));
                continue;
            }

            Forecast forecast = this.modelMapper.map(dto, Forecast.class);
            forecast.setCity(optionalCity.get());

            this.forecastRepository.save(forecast);
            result.append(String.format("Successfully imported forecast for %s%n", dto.getDayOfWeek()));
        }

        return result.toString();
    }


    @Override
    public String exportForecasts() {
        List<ForecastDTO> forecasts = this.forecastRepository.findSundayForecasts();

        StringBuilder result = new StringBuilder();
        for (ForecastDTO forecast : forecasts) {
            result.append(forecast.toString()).append(System.lineSeparator());
        }

        return result.toString().trim();
    }
}
