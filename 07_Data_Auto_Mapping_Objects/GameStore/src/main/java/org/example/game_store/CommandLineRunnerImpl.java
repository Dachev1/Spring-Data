package org.example.game_store;

import org.example.game_store.service.GameService;
import org.example.game_store.service.ShoppingCartService;
import org.example.game_store.service.UserService;
import org.example.game_store.service.dtos.gameDtos.GameAddDTO;
import org.example.game_store.service.dtos.userDtos.UserLoginDTO;
import org.example.game_store.service.dtos.userDtos.UserRegisterDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;
    private final ShoppingCartService shoppingCartService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.gameService = gameService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input = reader.readLine();
        while (!input.equals("exit")) {
            String[] tokens = input.split("\\|");
            String output = "";
            switch (tokens[0]) {
                case "RegisterUser" -> output = this.userService.registerUser(new UserRegisterDTO(tokens[1], tokens[2], tokens[3], tokens[4]));
                case "LoginUser" -> output = this.userService.loginInUser(new UserLoginDTO(tokens[1], tokens[2]));
                case "Logout" -> output = this.userService.logoutUser();
                case "AddGame" -> output = this.gameService.addGame(new GameAddDTO(tokens[1], Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]), tokens[4], tokens[5], tokens[6]));
                case "EditGame" -> {
                    long gameId = Long.parseLong(tokens[1]);

                    Map<String, String> updatesMap = Arrays.stream(tokens)
                            .skip(2)
                            .map(pair -> pair.split("="))
                            .collect(Collectors.toMap(
                                    keyValue -> keyValue[0],
                                    keyValue -> keyValue[1]
                            ));
                    output = this.gameService.editGame(gameId, updatesMap);
                }
                case "DeleteGame" -> output = this.gameService.deleteGame(Long.parseLong(tokens[1]));
                case "AllGames" -> output = this.gameService.printTitlesAndPriceForAllGames();
                case "DetailGame" -> output = this.gameService.printDetailsForTheGame(tokens[1]);
                case "AddItem" -> output = this.shoppingCartService.addItem(tokens[1]);
                case "RemoveItem" -> output = this.shoppingCartService.removeItem(tokens[1]);
                case "BuyItems" -> output = this.shoppingCartService.buyItems();
                default -> output = "There is no such command";
            }

            System.out.println(output);
            input = reader.readLine();
        }
    }
}
