package org.example.product_shop.service;

import java.io.FileNotFoundException;

public interface UserService {
    void seedUserData() throws FileNotFoundException;

    String getUsersAndProducts();
}
