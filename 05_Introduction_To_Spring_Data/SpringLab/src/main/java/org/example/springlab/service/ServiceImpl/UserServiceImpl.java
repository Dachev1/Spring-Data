package org.example.springlab.service.ServiceImpl;

import org.example.springlab.data.entities.User;
import org.example.springlab.data.repositories.UserRepository;
import org.example.springlab.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;

   public UserServiceImpl(UserRepository userRepository) {
       this.userRepository = userRepository;
   }

    @Override
    public void registerUser(User user) {
        this.userRepository.saveAndFlush(user);
    }
}
