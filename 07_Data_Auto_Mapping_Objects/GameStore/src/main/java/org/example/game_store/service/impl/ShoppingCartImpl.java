package org.example.game_store.service.impl;

import org.example.game_store.data.entities.Game;
import org.example.game_store.data.entities.Order;
import org.example.game_store.data.entities.User;
import org.example.game_store.data.repositories.GameRepository;
import org.example.game_store.data.repositories.OrderRepository;
import org.example.game_store.data.repositories.UserRepository;
import org.example.game_store.service.ShoppingCartService;
import org.example.game_store.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartImpl implements ShoppingCartService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final OrderRepository orderRepository;
    private Set<Game> gamesSet;

    public ShoppingCartImpl(UserService userService, UserRepository userRepository, GameRepository gameRepository, OrderRepository orderRepository,Set<Game> gamesToAdd) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
        this.gamesSet = new HashSet<>();
    }

    @Override
    public String addItem(String gameTitle) {
        User loggedUser = this.userService.getLoggedInUser();

        if (loggedUser == null) {
            return "You are not logged in\n";
        }

        Game game = findGameByTitle(gameTitle);
        if (game == null) {
            return String.format("Game with title %s does not exist\n", gameTitle);
        }

        if (this.gamesSet.contains(game)) {
            return String.format("Game with title %s is already in the cart\n", gameTitle);
        }

        this.gamesSet.add(game);

        return String.format("Game with title %s added to the cart\n", gameTitle);
    }

    @Override
    public String removeItem(String gameTitle) {
        Game game = findGameByTitle(gameTitle);
        if (game == null) {
            return String.format("Game with title %s does not exist\n", gameTitle);
        }

        this.gamesSet.remove(game);

        return String.format("Game with title %s removed from the cart\n", gameTitle);
    }

    @Override
    public String buyItems() {
        if (this.gamesSet.isEmpty()) {
            return "You are not added any games to your cart\n";
        }

        User loggedInUser = this.userService.getLoggedInUser();
        User user = this.userRepository.findByEmail(loggedInUser.getEmail());

        Set<String> gameTitles = this.gamesSet.stream()
                .map(Game::getTitle)
                .collect(Collectors.toSet());

        Order order = new Order(
                loggedInUser.getFullName(),
                loggedInUser.getEmail(),
                LocalDateTime.now(),
                gameTitles
        );

        this.orderRepository.saveAndFlush(order);

        user.setGames(this.gamesSet);

        this.userRepository.saveAndFlush(user);

        StringBuilder result = new StringBuilder("Successfully bought games:\n");
        this.gamesSet.forEach(game -> result.append(String.format(" - %s\n", game.getTitle())));

        this.gamesSet.clear();

        return result.toString().trim();
    }

    private Game findGameByTitle(String gameTitle) {
        return this.gameRepository.findByTitle(gameTitle);
    }
}

