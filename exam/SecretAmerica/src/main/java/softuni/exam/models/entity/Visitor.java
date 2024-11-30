package softuni.exam.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "visitors")
public class Visitor extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToOne(optional = false)
    @JoinColumn(name = "personal_data_id")
    private PersonalData personalData;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

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

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }
}