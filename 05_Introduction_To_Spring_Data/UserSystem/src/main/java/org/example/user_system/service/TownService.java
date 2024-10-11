package org.example.user_system.service;

import org.example.user_system.data.entities.Town;

import java.util.List;

public interface TownService {
    void saveTown(Town town);

    List<Town> findAllTowns();
}
