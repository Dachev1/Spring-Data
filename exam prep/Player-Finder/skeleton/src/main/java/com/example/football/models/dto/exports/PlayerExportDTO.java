package com.example.football.models.dto.exports;

public class PlayerExportDTO {

    private String firstName;
    private String lastName;
    private String position;
    private String teamName;
    private String stadiumName;

    public PlayerExportDTO(String firstName, String lastName, String position, String teamName, String stadiumName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.teamName = teamName;
        this.stadiumName = stadiumName;
    }

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }
}