package org.example.game_store.service;

import org.example.game_store.data.entities.User;
import org.example.game_store.service.dtos.userDtos.UserLoginDTO;
import org.example.game_store.service.dtos.userDtos.UserRegisterDTO;

public interface UserService {
    String registerUser(UserRegisterDTO registerUserDTO);

    String loginInUser(UserLoginDTO userLoginDTO);

    String logoutUser();

    User getLoggedInUser();
}
