package org.example.game_store.service.dtos.userDtos;

import jakarta.validation.constraints.Pattern;

public class UserLoginDTO {
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format. Please enter a valid email address containing an '@' symbol and a domain (e.g., example@domain.com).")
    private String email;

    private String password;

    public UserLoginDTO() {}

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

   public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
