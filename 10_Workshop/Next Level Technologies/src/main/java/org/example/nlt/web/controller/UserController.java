package org.example.nlt.web.controller;

import org.example.nlt.model.dto.UserRegisterDTO;
import org.example.nlt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Display the login page
    @GetMapping("/users/login")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");
        return modelAndView;
    }

    // Display the registration page
    @GetMapping("/users/register")
    public ModelAndView showRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/register");
        modelAndView.addObject("userRegisterDTO", new UserRegisterDTO());
        return modelAndView;
    }

    // Handle the registration form submission
    @PostMapping("/users/register")
    public ModelAndView registerUser(UserRegisterDTO userRegisterDTO) {
        userService.registerUser(userRegisterDTO);
        return new ModelAndView("redirect:/users/login"); // Redirect to login after successful registration
    }
}
