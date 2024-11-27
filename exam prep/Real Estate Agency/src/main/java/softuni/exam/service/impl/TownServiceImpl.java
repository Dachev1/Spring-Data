package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.TownSeedDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.validation.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {
    private static final String FILE_PATH = "src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder result = new StringBuilder();

        TownSeedDTO[] towns = this.gson.fromJson(readTownsFileContent(), TownSeedDTO[].class);

        for (TownSeedDTO townDTO : towns) {
            if (!this.validationUtil.isValid(townDTO) || this.townRepository.findByTownName(townDTO.getTownName()).isPresent()) {
                result.append(String.format("Invalid town%n"));
                continue;
            }

            Town town = this.modelMapper.map(townDTO, Town.class);
            this.townRepository.save(town);

            result.append(String.format("Successfully imported town %s - %d%n",
                    town.getTownName(), town.getPopulation()));
        }

        return result.toString();
    }
}
