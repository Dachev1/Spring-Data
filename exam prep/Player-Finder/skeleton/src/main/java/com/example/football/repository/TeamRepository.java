package com.example.football.repository;

import com.example.football.models.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(@NotNull @Size(min = 3) String name);

    Team findByName(String teamName);
}
