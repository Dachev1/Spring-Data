package org.example.car_dealer.service;

import java.io.IOException;

public interface SupplierService {
    void seedSuppliers() throws IOException;

    String getLocalSuppliersAsJson();
}
