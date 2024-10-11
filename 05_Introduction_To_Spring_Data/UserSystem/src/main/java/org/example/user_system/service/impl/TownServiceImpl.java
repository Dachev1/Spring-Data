package org.example.user_system.service.impl;

import org.example.user_system.data.entities.Town;
import org.example.user_system.data.repositories.TownRepository;
import org.example.user_system.service.TownService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;

    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public void saveTown(Town town) {
        this.townRepository.saveAndFlush(town);
    }

    @Override
    public List<Town> findAllTowns() {
        return this.townRepository.findAll();
    }
}