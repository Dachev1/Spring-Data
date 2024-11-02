package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.StarSeedDTO;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.enums.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class StarServiceImpl implements StarService {
    private final static String FILE_PATH = "src/main/resources/files/json/stars.json";

    private StarRepository starRepository;
    private final ConstellationRepository constellationRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder sb = new StringBuilder();

        StarSeedDTO[] starSeedDTOS = this.gson.fromJson(readStarsFileContent(), StarSeedDTO[].class);

        for (StarSeedDTO starSeedDTO : starSeedDTOS) {
            Optional<Star> optional = this.starRepository.findByName(starSeedDTO.getName());

            if (!this.validationUtil.isValid(starSeedDTO) || optional.isPresent()) {
                sb.append("Invalid star").append(System.lineSeparator());
                continue;
            }

            Star star = this.modelMapper.map(starSeedDTO, Star.class);
            star.setConstellation(this.constellationRepository.findById(starSeedDTO.getConstellation()).get());
            star.setStarType(StarType.valueOf(starSeedDTO.getStarType()));

            this.starRepository.saveAndFlush(star);

            sb.append(String.format("Successfully imported star %s - %.2f light years", star.getName(), star.getLightYears()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String exportStars() {
        StringBuilder sb = new StringBuilder();

        List<Star> stars = this.starRepository.findByStarTypeAndObserversIsEmptyOrderByLightYearsAsc(StarType.RED_GIANT);

        stars.forEach(star -> sb.append("Star: ")
                .append(star.getName()).append(System.lineSeparator())
                .append("   *Distance: ").append(String.format("%.2f", star.getLightYears())).append(" light years").append(System.lineSeparator())
                .append("   **Description: ").append(star.getDescription()).append(System.lineSeparator())
                .append("   ***Constellation: ").append(star.getConstellation().getName()).append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
