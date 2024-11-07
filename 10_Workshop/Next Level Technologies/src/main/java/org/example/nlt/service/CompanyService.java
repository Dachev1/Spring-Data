package org.example.nlt.service;

import jakarta.xml.bind.JAXBException;

public interface CompanyService {
    String getCompaniesXmlData();
    void importCompanies() throws JAXBException;

    boolean areImported();
}
