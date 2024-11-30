package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.exports.AttractionExportDTO;
import softuni.exam.models.dto.imports.AttractionSeedDTO;
import softuni.exam.models.entity.Attraction;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.AttractionService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@Service

public class AttractionServiceImpl implements AttractionService {
    private static final String FILE_ATTRACTION = "src/main/resources/files/json/attractions.json";

    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public AttractionServiceImpl(AttractionRepository attractionRepository, CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.attractionRepository.count() > 0;
    }

    @Override
    public String readAttractionsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_ATTRACTION));
    }

    @Override
    public String importAttractions() throws IOException {
        StringBuilder result = new StringBuilder();
        String fileContent = readAttractionsFileContent();

        AttractionSeedDTO[] dtos = this.gson.fromJson(fileContent, AttractionSeedDTO[].class);

        for (AttractionSeedDTO dto : dtos) {
            if (!this.validationUtil.isValid(dto) || this.attractionRepository.existsByName(dto.getName())) {
                result.append("Invalid attraction\n");
                continue;
            }

            Country country = this.countryRepository.findById(dto.getCountry()).orElse(null);
            if (country == null) {
                result.append("Invalid attraction\n");
                continue;
            }

            Attraction attraction = modelMapper.map(dto, Attraction.class);
            attraction.setCountry(country);

            attractionRepository.save(attraction);
            result.append(String.format("Successfully imported attraction %s\n", dto.getName()));
        }

        return result.toString().trim();
    }

    @Override
    public String exportAttractions() {
        StringBuilder result = new StringBuilder();

        List<AttractionExportDTO> attractions = this.attractionRepository.findAttractionsForExport(
                List.of("historical site", "archaeological site"), 300);

        for (AttractionExportDTO dto : attractions) {
            result.append(String.format("Attraction with ID%d:\n", dto.getId()));
            result.append(dto.toString()).append("\n");
        }

        return result.toString().trim();
    }

}

