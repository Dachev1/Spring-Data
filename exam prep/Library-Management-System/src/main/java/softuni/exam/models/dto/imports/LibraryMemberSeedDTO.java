package softuni.exam.models.dto.imports;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LibraryMemberSeedDTO {
    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;

    @Size(min = 2, max = 30)
    @NotNull
    private String lastName;

    @Size(min = 2, max = 40)
    private String address;

    @Size(min = 2, max = 20)
    @NotNull
    private String phoneNumber;

    public @Size(min = 2, max = 30) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Size(min = 2, max = 30) String firstName) {
        this.firstName = firstName;
    }

    public @Size(min = 2, max = 30) String getLastName() {
        return lastName;
    }

    public void setLastName(@Size(min = 2, max = 30) String lastName) {
        this.lastName = lastName;
    }

    public @Size(min = 2, max = 40) String getAddress() {
        return address;
    }

    public void setAddress(@Size(min = 2, max = 40) String address) {
        this.address = address;
    }

    public @Size(min = 2, max = 20) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Size(min = 2, max = 20) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
