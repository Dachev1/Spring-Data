package org.example.user_system.data.repositories;

import org.example.user_system.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByEmailEndingWith(String emailDomain);

    List<User> findAllByLastTimeLoggedInBefore(LocalDateTime date);

    void deleteAllByIsDeletedTrue();
}
