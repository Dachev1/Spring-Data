package org.example.product_shop.service.dtos.importDtos.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDto implements Serializable {

    @XmlAttribute(name = "first-name")
    @NotEmpty(message = "First name must not be empty")
    private String firstName;

    @XmlAttribute(name = "last-name")
    @NotEmpty(message = "Last name must not be empty")
    private String lastName;

    @XmlAttribute(name = "age")
    @Min(value = 16)
    private int age;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
