package org.example.game_store.service;

import org.example.game_store.service.dtos.gameDtos.GameAddDTO;

import java.util.Map;

public interface GameService {

    String addGame(GameAddDTO gameAddDTO);

    String editGame(long gameId, Map<String, String> updatesMap);

    String deleteGame(long gameId);

    String printTitlesAndPriceForAllGames();

    String printDetailsForTheGame(String title);
}
