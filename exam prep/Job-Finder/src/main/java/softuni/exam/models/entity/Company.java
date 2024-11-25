package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String website;

    @Column(name = "date_established", nullable = false)
    private LocalDate dateEstablished;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "company")
    private List<Job> jobs;

    @ManyToMany
    @JoinTable(
            name = "companies_jobs",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private List<Job> offeredJobs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LocalDate getDateEstablished() {
        return dateEstablished;
    }

    public void setDateEstablished(LocalDate dateEstablished) {
        this.dateEstablished = dateEstablished;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Job> getOfferedJobs() {
        return offeredJobs;
    }

    public void setOfferedJobs(List<Job> offeredJobs) {
        this.offeredJobs = offeredJobs;
    }
}
