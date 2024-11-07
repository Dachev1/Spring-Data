package org.example.nlt.service;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    String getEmployeesXmlData();

    @Transactional
    void importEmployees() throws JAXBException, IOException;

    List<String> getEmployeesAboveAge25();

    boolean areImported();
}
