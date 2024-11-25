package softuni.exam.models.dto.imports.xml;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class JobSeedDTO {

    @NotBlank
    @Size(min = 2, max = 40)
    private String jobTitle;

    @DecimalMin(value = "300.00")
    private double salary;

    @DecimalMin(value = "10.00")
    private double hoursAWeek;

    @NotBlank
    @Size(min = 5)
    private String description;

    @Min(1)
    private long companyId;

    public @NotBlank @Size(min = 2, max = 40) String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(@NotBlank @Size(min = 2, max = 40) String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @DecimalMin(value = "300.00")
    public double getSalary() {
        return salary;
    }

    public void setSalary(@DecimalMin(value = "300.00") double salary) {
        this.salary = salary;
    }

    @DecimalMin(value = "10.00")
    public double getHoursAWeek() {
        return hoursAWeek;
    }

    public void setHoursAWeek(@DecimalMin(value = "10.00") double hoursAWeek) {
        this.hoursAWeek = hoursAWeek;
    }

    public @NotBlank @Size(min = 5) String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank @Size(min = 5) String description) {
        this.description = description;
    }

    @Min(1)
    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(@Min(1) long companyId) {
        this.companyId = companyId;
    }
}
