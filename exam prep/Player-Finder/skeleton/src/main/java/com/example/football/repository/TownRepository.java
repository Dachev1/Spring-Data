package com.example.football.repository;

import com.example.football.models.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    boolean existsByName(@NotNull @Size(min = 2) String name);

    Town findByName(@NotNull String townName);
}
