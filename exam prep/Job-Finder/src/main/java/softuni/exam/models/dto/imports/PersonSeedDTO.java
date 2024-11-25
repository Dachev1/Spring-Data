package softuni.exam.models.dto.imports;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PersonSeedDTO {

    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @Size(min = 2, max = 13)
    private String phone;

    @NotBlank
    private String statusType;


    private long country;

    public @NotBlank @Size(min = 2, max = 30) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank @Size(min = 2, max = 30) String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank @Size(min = 2, max = 30) String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank @Size(min = 2, max = 30) String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @Size(min = 2, max = 13) String getPhone() {
        return phone;
    }

    public void setPhone(@Size(min = 2, max = 13) String phone) {
        this.phone = phone;
    }

    public @NotBlank String getStatusType() {
        return statusType;
    }

    public void setStatusType(@NotBlank String statusType) {
        this.statusType = statusType;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }
}
