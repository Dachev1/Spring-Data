package org.example.nlt.web.controller;

import jakarta.xml.bind.JAXBException;
import org.example.nlt.web.service.CompanyService;
import org.example.nlt.web.service.EmployeeService;
import org.example.nlt.web.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class ImportController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    private boolean companiesImported = false;
    private boolean employeesImported = false;
    private boolean projectsImported = false;

    public ImportController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/import/xml")
    public String showImportPage(Model model) {
        model.addAttribute("companiesImported", companiesImported);
        model.addAttribute("employeesImported", employeesImported);
        model.addAttribute("projectsImported", projectsImported);
        return "xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String showImportCompaniesPage(Model model) {
        model.addAttribute("companies", companyService.getCompaniesXmlData());
        return "xml/import-companies";
    }

    @PostMapping("/import/companies")
    public String importCompanies() {
        try {
            companyService.importCompanies();
            companiesImported = true;
        } catch (JAXBException e) {
            return "error/import-error";
        }
        return "redirect:/import/xml";
    }

    @GetMapping("/import/employees")
    public String showImportEmployeesPage(Model model) {
        model.addAttribute("employees", employeeService.getEmployeesXmlData());
        return "xml/import-employees";
    }

    @PostMapping("/import/employees")
    public String importEmployees() {
        try {
            employeeService.importEmployees();
            employeesImported = true;
        } catch (JAXBException | IOException e) {
            return "error/import-error";
        }
        return "redirect:/import/xml";
    }

    @GetMapping("/import/projects")
    public String showImportProjectsPage(Model model) {
        model.addAttribute("projects", projectService.getProjectsXmlData());
        return "xml/import-projects";
    }

    @PostMapping("/import/projects")
    public String importProjects() {
        try {
            projectService.importProjects();
            projectsImported = true;
        } catch (JAXBException | IOException e) {
            return "error/import-error";
        }
        return "redirect:/import/xml";
    }
}
