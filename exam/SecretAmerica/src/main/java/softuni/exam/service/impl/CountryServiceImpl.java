package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.CountrySeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CountryServiceImpl implements CountryService {
    private static final String FILE_COUNTRIES = "src/main/resources/files/json/countries.json";

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
    public String readCountryFileContent() throws IOException {
        return Files.readString(Path.of(FILE_COUNTRIES));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder result = new StringBuilder();

        String fileContent = readCountryFileContent();
        CountrySeedDTO[] countrySeedDTOS = gson.fromJson(fileContent, CountrySeedDTO[].class);

        for (CountrySeedDTO dto : countrySeedDTOS) {
            if (!validationUtil.isValid(dto) || countryRepository.existsByName(dto.getName())) {
                result.append("Invalid country\n");
                continue;
            }

            Country country = modelMapper.map(dto, Country.class);
            countryRepository.save(country);

            countryRepository.saveAndFlush(country);
            result.append(String.format("Successfully imported country %s\n", dto.getName()));
        }

        return result.toString().trim();
    }
}
