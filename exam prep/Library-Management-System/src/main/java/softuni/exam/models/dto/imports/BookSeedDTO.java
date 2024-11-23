package softuni.exam.models.dto.imports;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class BookSeedDTO {
    @Size(min = 3, max = 40)
    private String title;

    @Size(min = 3, max = 40)
    private String author;

    @Size(min = 5)
    private String description;

    private Boolean available;

    @NotNull
    private String genre;

    @Positive
    private Double rating;

    public @Size(min = 3, max = 40) String getTitle() {
        return title;
    }

    public void setTitle(@Size(min = 3, max = 40) String title) {
        this.title = title;
    }

    public @Size(min = 3, max = 40) String getAuthor() {
        return author;
    }

    public void setAuthor(@Size(min = 3, max = 40) String author) {
        this.author = author;
    }

    public @Size(min = 5) String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 5) String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public @NotNull String getGenre() {
        return genre;
    }

    public void setGenre(@NotNull String genre) {
        this.genre = genre;
    }

    public @Positive Double getRating() {
        return rating;
    }

    public void setRating(@Positive Double rating) {
        this.rating = rating;
    }
}
