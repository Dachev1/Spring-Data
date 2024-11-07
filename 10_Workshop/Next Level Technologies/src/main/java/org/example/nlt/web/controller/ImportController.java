package org.example.nlt.web.controller;

import jakarta.xml.bind.JAXBException;
import org.example.nlt.service.CompanyService;
import org.example.nlt.service.EmployeeService;
import org.example.nlt.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller

public class ImportController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public ImportController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/import/xml")
    public ModelAndView showImportPage() {
        ModelAndView mav = new ModelAndView("xml/import-xml");
        mav.addObject("companiesImported", Boolean.TRUE.equals(this.companyService.areImported()));
        mav.addObject("employeesImported", Boolean.TRUE.equals(this.employeeService.areImported()));
        mav.addObject("projectsImported", Boolean.TRUE.equals(this.projectService.areImported()));
        return mav;
    }

    @GetMapping("/import/companies")
    public ModelAndView showImportCompaniesPage() {
        ModelAndView mav = new ModelAndView("xml/import-companies");
        mav.addObject("companies", this.companyService.getCompaniesXmlData());
        return mav;
    }

    @PostMapping("/import/companies")
    public String importCompanies() throws JAXBException {
        this.companyService.importCompanies();
        return "redirect:/import/xml";
    }

    @GetMapping("/import/employees")
    public ModelAndView showImportEmployeesPage() {
        ModelAndView mav = new ModelAndView("xml/import-employees");
        mav.addObject("employees", this.employeeService.getEmployeesXmlData());
        return mav;
    }

    @PostMapping("/import/employees")
    public String importEmployees() throws JAXBException, IOException {
        this.employeeService.importEmployees();
        return "redirect:/import/xml";
    }

    @GetMapping("/import/projects")
    public ModelAndView showImportProjectsPage() {
        ModelAndView mav = new ModelAndView("xml/import-projects");
        mav.addObject("projects", this.projectService.getProjectsXmlData());
        return mav;
    }

    @PostMapping("/import/projects")
    public String importProjects() throws JAXBException, IOException {
        this.projectService.importProjects();
        return "redirect:/import/xml";
    }
}
