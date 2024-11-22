package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.VolcanoSeedDTO;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolcanoServiceImpl implements VolcanoService {
    private static final String FILE_PATH = "src/main/resources/files/json/volcanoes.json";

    private final VolcanoRepository volcanoRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder result = new StringBuilder();

        VolcanoSeedDTO[] volcanoSeedDTOs = gson.fromJson(readVolcanoesFileContent(), VolcanoSeedDTO[].class);

        for (VolcanoSeedDTO dto : volcanoSeedDTOs) {
            if (!validationUtil.isValid(dto) || volcanoRepository.findByName(dto.getName()).isPresent()) {
                result.append("Invalid volcano").append(System.lineSeparator());
                continue;
            }

            Volcano volcano = modelMapper.map(dto, Volcano.class);
            volcano.setCountry(countryRepository.findById(dto.getCountry().longValue()).orElse(null));

            if (volcano.getCountry() == null) {
                result.append("Invalid volcano").append(System.lineSeparator());
                continue;
            }

            addAndSaveAddedVolcano(volcano, null);
            result.append(String.format("Successfully imported volcano %s of type %s",
                            volcano.getName(), volcano.getVolcanoType()))
                    .append(System.lineSeparator());
        }

        return result.toString().trim();
    }

    @Override
    public Volcano findVolcanoById(Long volcanoId) {
        return volcanoRepository.findById(volcanoId).orElse(null);
    }

    @Override
    public void addAndSaveAddedVolcano(Volcano volcano, Volcanologist volcanologist) {
        Volcano existingVolcano = volcanoRepository.findByName(volcano.getName()).orElse(null);

        if (existingVolcano == null) {
            volcanoRepository.save(volcano);
            existingVolcano = volcano;
        }

        if (volcanologist != null) {
            volcanologist.setVolcano(existingVolcano);
        }
    }

    @Override
    public String exportVolcanoes() {
        List<Volcano> volcanoes = this.volcanoRepository.findAllActiveVolcanoesWithElevationAbove3000();

        if (volcanoes.isEmpty()) {
            return "No active volcanoes found with elevation above 3000m and last eruption date.";
        }

        return volcanoes.stream()
                .map(volcano -> String.format(
                        "Volcano: %s%n   *Located in: %s%n   **Elevation: %d%n   ***Last eruption on: %s",
                        volcano.getName(),
                        volcano.getCountry().getName(),
                        volcano.getElevation(),
                        volcano.getLastEruption() != null ? volcano.getLastEruption().toString() : "N/A"
                ))
                .collect(Collectors.joining("\n"));
    }
}
