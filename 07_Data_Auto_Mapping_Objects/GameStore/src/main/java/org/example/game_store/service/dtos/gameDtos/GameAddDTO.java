package org.example.game_store.service.dtos.gameDtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class GameAddDTO {
    @Pattern(regexp = "^[A-Z].{2,99}$", message = "Title must start with an uppercase letter and be between 3 and 100 characters.")
    private String title;

    @Positive(message = "Price must be a positive number.")
    private double price;

    @Positive(message = "Size must be a positive number.")
    private double size;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{11}$", message = "Trailer ID must be exactly 11 characters long and valid.")
    private String trailer;

    @Pattern(regexp = "^(http://|https://).+", message = "Thumbnail URL must start with http:// or https://.")
    private String thumbnailUrl;

    @Size(min = 20, message = "Description must be at least 20 characters long.")
    private String description;

    public GameAddDTO() {};

    public GameAddDTO(String title, double price, double size, String trailer, String thumbnailUrl, String description) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public Double getSize() {
        return size;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }
}
