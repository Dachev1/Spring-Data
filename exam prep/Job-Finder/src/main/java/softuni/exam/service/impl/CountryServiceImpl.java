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
import java.util.stream.Collectors;

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
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        String fileContent = readCountriesFileContent();

        CountrySeedDTO[] countrySeedDTOs = this.gson.fromJson(fileContent, CountrySeedDTO[].class);

        return Arrays.stream(countrySeedDTOs)
                .map(this::importCountry)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCountry(CountrySeedDTO dto) {
        if (! this.validationUtil.isValid(dto) || this.countryRepository.existsByName(dto.getName())) {
            return "Invalid Country";
        }

        Country country =  this.modelMapper.map(dto, Country.class);
        this.countryRepository.saveAndFlush(country);

        return String.format("Successfully imported Country: %s - %s", country.getName(), country.getCode());
    }
}
