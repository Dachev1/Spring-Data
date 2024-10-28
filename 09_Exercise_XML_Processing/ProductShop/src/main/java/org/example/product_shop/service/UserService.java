package org.example.product_shop.service;

import java.io.FileNotFoundException;

public interface UserService {
    void seedUsers() throws Exception;

    void exportUsersWithSoldProducts() throws Exception;

    void exportUsersWithSalesData() throws Exception;
}
