package com.example.football.service.impl;

import com.example.football.models.dto.exports.PlayerExportDTO;
import com.example.football.models.dto.imports.xml.player.PlayerRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.validation.ValidationUtil;
import com.example.football.util.xmlParser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final String FILE_PATH = "C:\\Users\\pffe3\\Desktop\\Player-Finder\\skeleton\\src\\main\\resources\\files\\xml\\players.xml";

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, TownRepository townRepository, StatRepository statRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException {
        StringBuilder result = new StringBuilder();

        PlayerRootDTO playerRootDTO = this.xmlParser.fromFile(FILE_PATH, PlayerRootDTO.class);

        playerRootDTO.getPlayers().stream().filter(this.validationUtil::isValid).forEach(playerDTO -> {
            if (!this.playerRepository.existsByEmail(playerDTO.getEmail())) {
                Player player = this.modelMapper.map(playerDTO, Player.class);

                player.setTown(this.townRepository.findByName(playerDTO.getTown().getName()));
                player.setTeam(this.teamRepository.findByName(playerDTO.getTeam().getName()));
                player.setStat(this.statRepository.findById(playerDTO.getStat().getId()).orElse(null));

                if (player.getTown() != null && player.getTeam() != null && player.getStat() != null) {
                    this.playerRepository.save(player);
                    result.append(String.format("Successfully imported Player %s %s - %s%n", player.getFirstName(), player.getLastName(), player.getPosition()));
                } else {
                    result.append("Invalid Player%n");
                }
            } else {
                result.append("Invalid Player%n");
            }
        });

        return result.toString();
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder result = new StringBuilder();

        List<PlayerExportDTO> bestPlayers = this.playerRepository.findBestPlayers();

        for (PlayerExportDTO player : bestPlayers) {
            result.append(String.format("Player - %s %s%n", player.getFirstName(), player.getLastName()));
            result.append(String.format("\tPosition - %s%n", player.getPosition()));
            result.append(String.format("\tTeam - %s%n", player.getTeamName()));
            result.append(String.format("\tStadium - %s%n", player.getStadiumName()));
        }

        return result.toString();
    }
}
