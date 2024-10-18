package org.example.dto.dtos;

import java.util.ArrayList;
import java.util.List;

public class ManagerDTO {

    private String firstName;
    private String lastName;
    private List<EmployeeDTO> employees;

    public ManagerDTO() {
        this.employees = new ArrayList<>();
    }

    public ManagerDTO(String firstName, String lastName, List<EmployeeDTO> employees) {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.employees = employees;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int employeeCount = (this.employees != null) ? this.employees.size() : 0;
        sb.append(String.format("%s %s | Employees: %d%n", this.firstName, this.lastName, employeeCount));

        if (this.employees != null) {
            for (EmployeeDTO employee : this.employees) {
                sb.append(String.format("    - %s %s %n", employee.getFirstName(), employee.getLastName()));
            }
        }

        return sb.toString();
    }
}
