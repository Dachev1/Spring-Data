package org.example.user_system.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.user_system.data.entities.base_entity.BaseEntity;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {

    @Column(nullable = false)
    @NotBlank(message = "Town name is required")
    @Size(min = 2, max = 100, message = "Town name must be between 2 and 100 characters")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Country name is required")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters")
    private String country;

    public Town() {}

    public Town(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public @NotBlank(message = "Town name is required") @Size(min = 2, max = 100, message = "Town name must be between 2 and 100 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Town name is required") @Size(min = 2, max = 100, message = "Town name must be between 2 and 100 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Country name is required") @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters") String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank(message = "Country name is required") @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters") String country) {
        this.country = country;
    }
}
