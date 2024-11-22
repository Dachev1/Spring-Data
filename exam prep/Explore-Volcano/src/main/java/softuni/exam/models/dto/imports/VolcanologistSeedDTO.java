package softuni.exam.models.dto.imports;

import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "volcanologist")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistSeedDTO {

    @XmlElement(name = "first_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;

    @XmlElement(name = "last_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String lastName;

    @XmlElement
    @Positive
    @NotNull
    private BigDecimal salary;

    @XmlElement
    @Min(18)
    @Max(80)
    @NotNull
    private Integer age;

    @XmlElement(name = "exploring_from")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @PastOrPresent
    @NotNull
    private LocalDate exploringFrom;

    @XmlElement(name = "exploring_volcano_id")
    @NotNull
    private Long exploringVolcanoId;

    // Getters and Setters
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(LocalDate exploringFrom) {
        this.exploringFrom = exploringFrom;
    }

    public Long getExploringVolcanoId() {
        return exploringVolcanoId;
    }

    public void setExploringVolcanoId(Long exploringVolcanoId) {
        this.exploringVolcanoId = exploringVolcanoId;
    }
}
