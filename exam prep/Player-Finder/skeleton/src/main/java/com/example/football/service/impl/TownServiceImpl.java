package com.example.football.service.impl;

import com.example.football.models.dto.imports.TownSeedDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.validation.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {
    private static final String FILE_PATH = "C:\\Users\\pffe3\\Desktop\\Player-Finder\\skeleton\\src\\main\\resources\\files\\json\\towns.json";

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
        String townsFileContent = readTownsFileContent();

        TownSeedDTO[] towns = this.gson.fromJson(townsFileContent, TownSeedDTO[].class);

        for (TownSeedDTO townDto : towns) {
            if (!this.validationUtil.isValid(townDto) || this.townRepository.existsByName(townDto.getName())) {
                result.append("Invalid Town").append(System.lineSeparator());
                continue;
            }

            Town town = this.modelMapper.map(townDto, Town.class);
            this.townRepository.saveAndFlush(town);

            result.append(String.format(
                    "Successfully imported Town %s - %d",
                    town.getName(),
                    town.getPopulation()
            )).append(System.lineSeparator());
        }

        return result.toString().trim();
    }
}
