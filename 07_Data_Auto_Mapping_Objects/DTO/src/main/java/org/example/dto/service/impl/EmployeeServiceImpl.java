package org.example.dto.service.impl;

import org.example.dto.date.entities.Employee;
import org.example.dto.date.repositories.EmployeeRepository;
import org.example.dto.dtos.EmployeeDTO;
import org.example.dto.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> getAllEmployeesAfterBirthDate(LocalDate birthDate) {
        List<Employee> employees = this.employeeRepository.findAllByBirthDateAfter(birthDate);

        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveEmployee(Employee employee) {
        this.employeeRepository.saveAndFlush(employee);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        if (employee.getManager() != null) {
            employeeDTO.setManagerLastName(employee.getManager().getLastName());
        } else {
            employeeDTO.setManagerLastName("[no manager]");
        }

        return employeeDTO;
    }
}