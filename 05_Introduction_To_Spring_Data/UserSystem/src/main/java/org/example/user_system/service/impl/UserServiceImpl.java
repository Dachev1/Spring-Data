package org.example.user_system.service.impl;

import org.example.user_system.data.entities.User;
import org.example.user_system.data.repositories.UserRepository;
import org.example.user_system.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void findAllByEmailEndingWith(String provider) {
        this.userRepository
                .findAllByEmailEndingWith("@" + provider)
                .forEach(user -> System.out.printf("%s -> %s%n", user.getUsername(), user.getEmail()));
    }

    @Override
    public int removeInactiveUsers(LocalDateTime date) {
        List<User> inactiveUsers = userRepository.findAllByLastTimeLoggedInBefore(date);
        inactiveUsers.forEach(user -> user.setDeleted(true)); // Mark users as deleted
        userRepository.saveAll(inactiveUsers); // Save changes to the database
        return inactiveUsers.size(); // Return number of inactive users updated
    }

    @Override
    public void deleteMarkedUsers() {
        userRepository.deleteAllByIsDeletedTrue(); // Delete all users marked as deleted
    }
}