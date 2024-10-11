package org.example.user_system.data.repositories;

import org.example.user_system.data.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
}
