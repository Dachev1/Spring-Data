package org.example.dto.date.repositories;

import org.example.dto.date.entities.Employee;
import org.example.dto.dtos.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByBirthDateAfter(LocalDate birthDate);
}
