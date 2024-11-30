package softuni.exam.models.dto.imports.xml.personalData;

import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import softuni.exam.util.adapters.LocalDateAdapter;

import java.time.LocalDate;

@XmlRootElement(name = "personal_data")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDataDTO {

    @XmlElement(name = "age")
    @Min(1)
    @Max(100)
    private Integer age;

    @XmlElement(name = "gender")
    @Pattern(regexp = "M|F$")
    private String gender;

    @XmlElement(name = "birth_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @Past
    private LocalDate birthDate;

    @XmlElement(name = "card_number")
    @NotBlank
    @Size(min = 9, max = 9)
    private String cardNumber;

    // Getters and setters
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
