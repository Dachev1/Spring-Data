package org.example.dto;

import org.example.dto.date.entities.Employee;
import org.example.dto.dtos.EmployeeDTO;
import org.example.dto.dtos.ManagerDTO;
import org.example.dto.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final EmployeeService employeeService;

    public CommandLineRunnerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Employee manager = new Employee("FirstN", "LastN", 100.000, LocalDate.now(), "Plovdiv", false, null, new ArrayList<>());

        Employee employee1 = new Employee
                ("Pepa", "S", 55.99, LocalDate.of(1999, 01, 01), "Malevo", true, manager, null);

        Employee employee2 = new Employee
                ("Delqna", "Bashova", 1000, LocalDate.of(2005, 01, 01), "Svilengrad", true, manager, null);

        manager.getEmployees().add(employee1);



//        ModelMapper modelMapper = new ModelMapper();

//        EmployeeDTO employeeDto = modelMapper.map(employee1, EmployeeDTO.class);
//        System.out.println(employeeDto);
//
//        ManagerDTO managerDTO = modelMapper.map(manager, ManagerDTO.class);
//        System.out.println(managerDTO);

//        this.employeeService.saveEmployee(manager);
//        this.employeeService.saveEmployee(employee1);
//        this.employeeService.saveEmployee(employee2);

        List<EmployeeDTO> allEmployeesAfterBirthDate = this.employeeService.getAllEmployeesAfterBirthDate(LocalDate.of(2000, 01, 01));
        allEmployeesAfterBirthDate.forEach(System.out::println);
    }
}
