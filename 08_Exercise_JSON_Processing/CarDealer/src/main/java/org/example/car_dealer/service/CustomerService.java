package org.example.car_dealer.service;

import java.io.IOException;

public interface CustomerService {
    void seedCustomers() throws IOException;

    String getOrderedCustomersWithSalesAsJson();

    String getTotalSalesByCustomerAsJson();
}
