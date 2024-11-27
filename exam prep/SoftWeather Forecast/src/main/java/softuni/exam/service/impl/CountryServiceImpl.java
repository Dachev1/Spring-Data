package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.CountrySeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CountryServiceImpl implements CountryService {
    private static final String FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder result = new StringBuilder();

        CountrySeedDTO[] countrySeedDTOs = this.gson.fromJson(readCountriesFromFile(), CountrySeedDTO[].class);

        for (CountrySeedDTO countrySeedDTO : countrySeedDTOs) {
            if (this.validationUtil.isValid(countrySeedDTO) &&
                    !this.countryRepository.existsByCountryName(countrySeedDTO.getCountryName())) {

                Country country = modelMapper.map(countrySeedDTO, Country.class);
                this.countryRepository.save(country);

                result.append(String.format(
                                "Successfully imported country %s - %s",
                                country.getCountryName(),
                                country.getCurrency()))
                        .append(System.lineSeparator());
            } else {
                result.append("Invalid country").append(System.lineSeparator());
            }
        }

        return result.toString().trim();
    }
}
