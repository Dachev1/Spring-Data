package com.example.football.service.impl;

import com.example.football.models.dto.imports.TeamSeedDTO;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.validation.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TeamServiceImpl implements TeamService {
    private static final String FILE_PATH = "C:\\Users\\pffe3\\Desktop\\Player-Finder\\skeleton\\src\\main\\resources\\files\\json\\teams.json";

    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder result = new StringBuilder();
        String teamsFileContent = readTeamsFileContent();

        TeamSeedDTO[] teams = this.gson.fromJson(teamsFileContent, TeamSeedDTO[].class);

        for (TeamSeedDTO teamDto : teams) {
            if (!this.validationUtil.isValid(teamDto)
                    || this.teamRepository.existsByName(teamDto.getName())
                    || !this.townRepository.existsByName(teamDto.getTownName())) {
                result.append("Invalid Team").append(System.lineSeparator());
                continue;
            }

            Team team = this.modelMapper.map(teamDto, Team.class);
            team.setTown(this.townRepository.findByName(teamDto.getTownName()));
            this.teamRepository.saveAndFlush(team);

            result.append(String.format(
                    "Successfully imported Team %s - %d",
                    team.getName(),
                    team.getFanBase()
            )).append(System.lineSeparator());
        }

        return result.toString().trim();
    }
}
