package org.example.nlt.web.controller;

import org.example.nlt.service.CompanyService;
import org.example.nlt.service.EmployeeService;
import org.example.nlt.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller

public class HomeController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public HomeController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView home = new ModelAndView("home");
        home.addObject("areImported", areAllImported());
        return home;
    }

    @GetMapping("/export/project-if-finished")
    public ModelAndView exportFinishedProjects() {
        ModelAndView mav = new ModelAndView("export/export-project-if-finished");
        List<String> finishedProjects = this.projectService.getFinishedProjects();
        mav.addObject("projectsIfFinished", String.join("\n", finishedProjects));
        return mav;
    }

    @GetMapping("/export/employees-above")
    public ModelAndView exportEmployeesAboveAge() {
        ModelAndView mav = new ModelAndView("export/export-employees-with-age");
        List<String> employeesAbove25 = this.employeeService.getEmployeesAboveAge25();
        mav.addObject("employeesAbove", String.join("\n", employeesAbove25));
        return mav;
    }

    private boolean areAllImported() {
        return companyService.areImported() && employeeService.areImported() && projectService.areImported();
    }
}
