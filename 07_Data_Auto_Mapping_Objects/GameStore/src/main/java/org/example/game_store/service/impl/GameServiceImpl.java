package org.example.game_store.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.game_store.data.entities.Game;
import org.example.game_store.data.entities.User;
import org.example.game_store.data.repositories.GameRepository;
import org.example.game_store.service.GameService;
import org.example.game_store.service.UserService;
import org.example.game_store.service.dtos.gameDtos.GameAddDTO;
import org.example.game_store.service.dtos.gameDtos.GamePrintDetailsDTO;
import org.example.game_store.service.dtos.gameDtos.GamesAllDTO;
import org.example.game_store.utils.ValidatorComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final ModelMapper modelMapper;
    private final ValidatorComponent validatorComponent;
    private final GameRepository gameRepository;
    private final UserService userService;

    public GameServiceImpl(ModelMapper modelMapper, ValidatorComponent validatorComponent, GameRepository gameRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.validatorComponent = validatorComponent;
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public String addGame(GameAddDTO gameAddDTO) {
        String adminCheckResult = isAdminUser();
        if (adminCheckResult != null) {
            return adminCheckResult;  // Return the error message if the user is not an admin
        }

        if (!this.validatorComponent.isValid(gameAddDTO)) {
            return this.validatorComponent.validate(gameAddDTO).stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        Game mappedGame = this.modelMapper.map(gameAddDTO, Game.class);
        this.gameRepository.saveAndFlush(mappedGame);

        return String.format("Added %s\n", mappedGame.getTitle());
    }

    @Override
    public String editGame(long gameId, Map<String, String> updatesMap) {
        String adminCheckResult = isAdminUser();
        if (adminCheckResult != null) {
            return adminCheckResult;  // Return the error message if the user is not an admin
        }

        Optional<Game> optional = this.gameRepository.findById(gameId);

        if (optional.isEmpty()) {
            return "Game with id " + gameId + " does not exist.\n";
        }

        Game game = optional.get();

        updatesMap.forEach((key, value) -> {
            switch (key) {
                case "title" -> game.setTitle(value);
                case "description" -> game.setDescription(value);
                case "price" -> game.setPrice(Double.parseDouble(value));
                case "size" -> game.setSize(Double.parseDouble(value));
                case "trailer" -> game.setTrailer(value);
                case "thumbnailUrl" -> game.setThumbnailUrl(value);
            }
        });

        this.gameRepository.saveAndFlush(game);

        return String.format("Edited %s\n", game.getTitle());
    }

    @Override
    public String deleteGame(long gameId) {
        Optional<Game> optional = this.gameRepository.findById(gameId);

        if (optional.isEmpty()) {
            return "Game with id " + gameId + " does not exist.\n";
        }

        String output = String.format("Deleted %s\n", optional.get().getTitle());
        this.gameRepository.delete(optional.get());

        return output;
    }

    @Override
    public String printTitlesAndPriceForAllGames() {
        return this.gameRepository.findAll().stream()
                .map(game -> this.modelMapper.map(game, GamesAllDTO.class).toString())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String printDetailsForTheGame(String title) {
        Game game = this.gameRepository.findByTitle(title);

        if (game == null) {
            return "Game with title " + title + " does not exist.\n";
        }

        return this.modelMapper.map(game, GamePrintDetailsDTO.class).toString();
    }

    private String isAdminUser() {
        User loggedUser = this.userService.getLoggedInUser();

        if (loggedUser == null) {
            return "No user is logged in.\n";
        }

        if (!loggedUser.isAdmin()) {
            return "Cannot perform the operation, because the user is not an admin.\n";
        }

        return null;  // Return null if the user is an admin (no error)
    }
}
