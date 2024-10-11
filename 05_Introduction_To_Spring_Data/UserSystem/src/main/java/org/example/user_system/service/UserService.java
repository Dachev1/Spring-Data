package org.example.user_system.service;

import org.example.user_system.data.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    void findAllByEmailEndingWith(String provider);

    void saveUser(User user);

    List<User> findAllUsers();

    int removeInactiveUsers(LocalDateTime date);

    void deleteMarkedUsers();
}
