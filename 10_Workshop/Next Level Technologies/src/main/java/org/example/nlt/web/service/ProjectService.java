package org.example.nlt.web.service;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface ProjectService {
    String getProjectsXmlData();

    @Transactional
    void importProjects() throws JAXBException, IOException;
}
