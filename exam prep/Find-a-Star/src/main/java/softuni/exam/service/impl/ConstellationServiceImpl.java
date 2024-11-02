package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.ConstellationSeedDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private final static String FILE_PATH = "src/main/resources/files/json/constellations.json";

    private final ConstellationRepository constellationRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.constellationRepository = constellationRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder sb = new StringBuilder();

        ConstellationSeedDTO[] constellationSeedDTOS = this.gson.fromJson(readConstellationsFromFile(), ConstellationSeedDTO[].class);


        for (ConstellationSeedDTO constellationSeedDTO : constellationSeedDTOS) {

        Optional<Constellation> optional = this.constellationRepository.findByName(constellationSeedDTO.getName());
            if (!this.validationUtil.isValid(constellationSeedDTO) || optional.isPresent()) {
                sb.append("Invalid constellation").append(System.lineSeparator());
                continue;
            }

            Constellation constellation = this.modelMapper.map(constellationSeedDTO, Constellation.class);
            this.constellationRepository.saveAndFlush(constellation);

            sb.append(String.format("Successfully imported constellation %s - %s", constellation.getName(), constellation.getDescription()))
                    .append(System.lineSeparator());

        }

        return sb.toString().trim();
    }
}
