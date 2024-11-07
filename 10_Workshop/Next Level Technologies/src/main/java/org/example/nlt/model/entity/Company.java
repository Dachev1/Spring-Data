package org.example.nlt.model.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Company extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "company")
    private List<Project> projects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}