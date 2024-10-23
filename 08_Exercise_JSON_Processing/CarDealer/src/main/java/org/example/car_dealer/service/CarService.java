package org.example.car_dealer.service;

import java.io.IOException;

public interface CarService {
    void seedCars() throws IOException;

    String getALlCarsByMake(String make);

    String getCarsWithTheirPartsAsJson();
}
