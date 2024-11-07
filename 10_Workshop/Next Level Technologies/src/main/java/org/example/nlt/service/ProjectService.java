package org.example.nlt.service;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.List;

public interface ProjectService {
    String getProjectsXmlData();

    @Transactional
    void importProjects() throws JAXBException, IOException;

    List<String> getFinishedProjects();

    boolean areImported();
}
