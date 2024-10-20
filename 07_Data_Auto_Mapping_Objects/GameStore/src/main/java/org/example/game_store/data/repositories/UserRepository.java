package org.example.game_store.data.repositories;

import org.example.game_store.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

    User findByEmail(String email);
}
