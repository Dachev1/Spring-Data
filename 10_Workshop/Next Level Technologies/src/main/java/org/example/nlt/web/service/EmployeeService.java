package org.example.nlt.web.service;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface EmployeeService {
    String getEmployeesXmlData();

    @Transactional
    void importEmployees() throws JAXBException, IOException;
}
