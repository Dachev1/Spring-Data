package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.CitySeedDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    private static final String FILE_PATH = "src/main/resources/files/json/cities.json";

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder result = new StringBuilder();

        CitySeedDTO[] citySeedDTOs = this.gson.fromJson(readCitiesFileContent(), CitySeedDTO[].class);

        for (CitySeedDTO citySeedDTO : citySeedDTOs) {
            if (this.validationUtil.isValid(citySeedDTO) &&
                    !this.cityRepository.existsByCityName(citySeedDTO.getCityName())) {

                City city = this.modelMapper.map(citySeedDTO, City.class);

                Optional<Country> country = this.countryRepository.findById(citySeedDTO.getCountry());
                if (country.isPresent()) {
                    city.setCountry(country.get());
                    this.cityRepository.save(city);

                    result.append(String.format(
                                    "Successfully imported city %s - %d",
                                    city.getCityName(),
                                    city.getPopulation()))
                            .append(System.lineSeparator());
                } else {
                    result.append("Invalid city").append(System.lineSeparator());
                }
            } else {
                result.append("Invalid city").append(System.lineSeparator());
            }
        }

        return result.toString().trim();
    }
}
