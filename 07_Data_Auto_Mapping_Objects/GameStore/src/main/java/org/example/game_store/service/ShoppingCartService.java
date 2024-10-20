package org.example.game_store.service;

public interface ShoppingCartService {
    String addItem(String gameTitle);

    String removeItem(String gameTitle);

    String buyItems();
}
