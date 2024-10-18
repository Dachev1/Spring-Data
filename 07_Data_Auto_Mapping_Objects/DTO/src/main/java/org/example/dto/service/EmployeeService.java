package org.example.dto.service;

import org.example.dto.date.entities.Employee;
import org.example.dto.dtos.EmployeeDTO;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService  {
    List<EmployeeDTO> getAllEmployeesAfterBirthDate(LocalDate birthDate);

    void saveEmployee(Employee employee);
}
