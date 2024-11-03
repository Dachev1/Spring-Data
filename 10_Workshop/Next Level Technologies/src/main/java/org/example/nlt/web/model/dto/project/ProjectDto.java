package org.example.nlt.web.model.dto.project;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.example.nlt.web.model.dto.company.CompanyDto;
import org.example.nlt.web.util.LocalDateAdapter;

import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectDto {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "start-date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @XmlElement(name = "is-finished")
    private Boolean isFinished;

    @XmlElement(name = "payment")
    private BigDecimal payment;

    @XmlElement(name = "company")
    private CompanyDto company;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public CompanyDto getCompany() {
        return company;
    }
}
