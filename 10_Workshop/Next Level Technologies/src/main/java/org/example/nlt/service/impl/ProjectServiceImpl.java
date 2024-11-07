package org.example.nlt.service.impl;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBException;
import org.example.nlt.model.dto.project.ProjectDto;
import org.example.nlt.model.dto.project.ProjectsWrapper;
import org.example.nlt.model.entity.Company;
import org.example.nlt.model.entity.Project;
import org.example.nlt.repository.CompanyRepository;
import org.example.nlt.repository.ProjectRepository;
import org.example.nlt.service.ProjectService;
import org.example.nlt.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final String PROJECTS_XML_PATH = "src/main/resources/files/xmls/projects.xml";

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }


    @Override
    public String getProjectsXmlData() {
        return readXmlFile(PROJECTS_XML_PATH);
    }

    @Override
    @Transactional
    public void importProjects() throws JAXBException {
        if (!areImported()) {
            List<ProjectDto> projectDtos = this.xmlParser.fromXml(readXmlFile(PROJECTS_XML_PATH), ProjectsWrapper.class).getProjects();

            List<Project> projects = projectDtos.stream().map(dto -> {
                Project project = this.modelMapper.map(dto, Project.class);

                if (dto.getCompany() != null && dto.getCompany().getName() != null) {
                    Company company = this.companyRepository.findByName(dto.getCompany().getName()).orElseGet(() -> this.modelMapper.map(dto.getCompany(), Company.class));
                    project.setCompany(company);
                }

                return project;
            }).collect(Collectors.toList());

            this.projectRepository.saveAll(projects);
        }
    }

    @Override
    public List<String> getFinishedProjects() {
        return projectRepository.findAllByIsFinishedTrue()
                .stream()
                .map(project -> String.format("Project Name: %s, Description: %s", project.getName(), project.getDescription()))
                .collect(Collectors.toList());
    }


    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    private String readXmlFile(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error reading XML file from path: " + filePath, e);
        }
    }
}
