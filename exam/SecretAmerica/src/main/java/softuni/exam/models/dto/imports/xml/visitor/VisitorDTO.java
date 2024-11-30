package softuni.exam.models.dto.imports.xml.visitor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "visitor")
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitorDTO {

    @NotBlank
    @Size(min = 2, max = 20)
    @XmlElement(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 20)
    @XmlElement(name = "last_name")
    private String lastName;

    @NotNull
    @XmlElement(name = "attraction_id")
    private Long attractionId;

    @NotNull
    @XmlElement(name = "country_id")
    private Long countryId;

    @NotNull
    @XmlElement(name = "personal_data_id")
    private Long personalDataId;

    // Getters and setters
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

    public Long getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getPersonalDataId() {
        return personalDataId;
    }

    public void setPersonalDataId(Long personalDataId) {
        this.personalDataId = personalDataId;
    }
}
