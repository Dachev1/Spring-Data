package org.example.dto.dtos;

public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private double salary;
    private String managerLastName;

    public EmployeeDTO() {}

    public EmployeeDTO(String firstName, String lastName, double salary, String managerLastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerLastName = managerLastName;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f – Manager: %s",
                this.firstName,
                this.lastName,
                this.salary,
                this.managerLastName);
    }
}
