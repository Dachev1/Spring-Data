package org.example.game_store.service.impl;

import jakarta.validation.ConstraintViolation;
import org.example.game_store.data.entities.User;
import org.example.game_store.data.repositories.UserRepository;
import org.example.game_store.service.UserService;
import org.example.game_store.service.dtos.userDtos.UserLoginDTO;
import org.example.game_store.service.dtos.userDtos.UserRegisterDTO;
import org.example.game_store.utils.ValidatorComponent;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private User loggedInUser;

    private final ModelMapper modelMapper;
    private final ValidatorComponent validatorComponent;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidatorComponent validatorComponent) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validatorComponent = validatorComponent;
    }

    @Override
    public String registerUser(UserRegisterDTO registerUserDTO) {
        if (isInvalidEntity(registerUserDTO)) return this.validatorComponent.validate(registerUserDTO)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));

        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) {
            return "Password and Confirm Password do not match\n";
        }

        User mappedUser = this.modelMapper.map(registerUserDTO, User.class);

        if(this.userRepository.count() == 0) {
            mappedUser.setAdmin(true);
        }

        this.userRepository.saveAndFlush(mappedUser);

        return String.format("%s was registered\n", mappedUser.getFullName());
    }


    @Override
    public String loginInUser(UserLoginDTO userLoginDTO) {
        if (isInvalidEntity(userLoginDTO)) return this.validatorComponent.validate(userLoginDTO)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));

        if (loggedInUser != null) {
            return "Already logged in user\n";
        }

        if (this.userRepository.existsByEmail(userLoginDTO.getEmail())) {
            User user = this.userRepository.findByEmail(userLoginDTO.getEmail());

            if (user.getPassword().equals(userLoginDTO.getPassword())) {
                this.setLoggedInUser(user);
                return String.format("Successfully logged in %s\n", user.getFullName());
            }
        }

        return "Incorrect username / password";
    }

    @Override
    public String logoutUser() {
        if (getLoggedInUser() == null) {
            return "Cannot log out. No user was logged in.";
        } else {
            String output = String.format("%s logged out successfully\n", this.getLoggedInUser().getFullName());
            this.setLoggedInUser(null);
            return output;
        }
    }

    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    private <T> boolean isInvalidEntity(T entity) {
        return !this.validatorComponent.isValid(entity);
    }
}
