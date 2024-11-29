package com.example.football.models.dto.imports.xml.player;

import com.example.football.util.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerDTO {

    @XmlElement(name = "first-name")
    private String firstName;

    @XmlElement(name = "last-name")
    private String lastName;

    @XmlElement(name = "email")
    private String email;

    @XmlElement(name = "birth-date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthDate;

    @XmlElement(name = "position")
    private String position;

    @XmlElement(name = "town")
    private TownDTO town;

    @XmlElement(name = "team")
    private TeamDTO team;

    @XmlElement(name = "stat")
    private StatDTO stat;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public TownDTO getTown() {
        return town;
    }

    public void setTown(TownDTO town) {
        this.town = town;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    public StatDTO getStat() {
        return stat;
    }

    public void setStat(StatDTO stat) {
        this.stat = stat;
    }
}
