package org.example.dto.date.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private double salary;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String address;

    @Column(name = "is_on_holiday")
    private boolean isOnHoliday;

    // Many employees can have the same manager (self-referencing relationship)
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    // One manager can manage many employees
    @OneToMany(mappedBy = "manager")
    private List<Employee> employees;

    public Employee() {
        this.employees = new ArrayList<>();
    }

    public Employee(String firstName, String lastName, double salary, LocalDate birthDate, String address, boolean isOnHoliday, Employee manager, List<Employee> employees) {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthDate = birthDate;
        this.address = address;
        this.isOnHoliday = isOnHoliday;
        this.manager = manager;
        this.employees = employees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOnHoliday() {
        return isOnHoliday;
    }

    public void setOnHoliday(boolean onHoliday) {
        isOnHoliday = onHoliday;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
