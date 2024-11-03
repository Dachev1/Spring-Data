package org.example.nlt.web.service;

import jakarta.xml.bind.JAXBException;

public interface CompanyService {
    String getCompaniesXmlData();
    void importCompanies() throws JAXBException;
}
