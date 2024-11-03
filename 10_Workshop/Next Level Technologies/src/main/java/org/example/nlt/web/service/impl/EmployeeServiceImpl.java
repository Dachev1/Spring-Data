package org.example.nlt.web.service.impl;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBException;
import org.example.nlt.web.model.dto.employee.EmployeeDto;
import org.example.nlt.web.model.dto.employee.EmployeesWrapper;
import org.example.nlt.web.model.entity.Employee;
import org.example.nlt.web.model.entity.Project;
import org.example.nlt.web.repository.EmployeeRepository;
import org.example.nlt.web.repository.ProjectRepository;
import org.example.nlt.web.service.EmployeeService;
import org.example.nlt.web.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEES_XML_PATH = "src/main/resources/files/xmls/employees.xml";

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.configureModelMapper();
    }

    private void configureModelMapper() {
        this.modelMapper.typeMap(EmployeeDto.class, Employee.class).addMappings(mapper -> {
            mapper.skip(Employee::setId); // Skip ID setting for new entities
        });
    }

    @Override
    public String getEmployeesXmlData() {
        return this.readXmlFile(EMPLOYEES_XML_PATH);
    }

    @Transactional
    @Override
    public void importEmployees() throws JAXBException, IOException {
        List<EmployeeDto> employeeDtos = this.xmlParser.fromXml(this.readXmlFile(EMPLOYEES_XML_PATH), EmployeesWrapper.class).getEmployees();

        List<Employee> employees = employeeDtos.stream()
                .map(dto -> this.mapToEmployee(dto))
                .collect(Collectors.toList());

        this.employeeRepository.saveAll(employees);
    }

    private Employee mapToEmployee(EmployeeDto dto) {
        Employee employee = this.modelMapper.map(dto, Employee.class);

        if (dto.getProject() != null && dto.getProject().getName() != null) {
            Project project = this.projectRepository.findByName(dto.getProject().getName())
                    .orElseGet(() -> this.modelMapper.map(dto.getProject(), Project.class));
            employee.setProject(project);
        }

        return employee;
    }

    private String readXmlFile(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error reading XML file from path: " + filePath, e);
        }
    }
}
