package org.example.user_system.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.user_system.data.entities.base_entity.BaseEntity;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    @Column(nullable = false)
    @NotBlank(message = "Picture title is required")
    @Size(min = 2, max = 100, message = "Picture title must be between 2 and 100 characters")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Picture caption is required")
    @Size(min = 2, max = 255, message = "Picture caption must be between 2 and 255 characters")
    private String caption;

    @Column(nullable = false)
    @NotBlank(message = "Picture path is required")
    private String path;

    @ManyToMany
    @JoinTable(
            name = "pictures_albums",
            joinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id")
    )
    private Set<Album> albums = new HashSet<>();

    public Picture() {
    }

    public Picture(String title, String caption, String path) {
        this.title = title;
        this.caption = caption;
        this.path = path;
    }

    public @NotBlank(message = "Picture title is required") @Size(min = 2, max = 100, message = "Picture title must be between 2 and 100 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Picture title is required") @Size(min = 2, max = 100, message = "Picture title must be between 2 and 100 characters") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Picture caption is required") @Size(min = 2, max = 255, message = "Picture caption must be between 2 and 255 characters") String getCaption() {
        return caption;
    }

    public void setCaption(@NotBlank(message = "Picture caption is required") @Size(min = 2, max = 255, message = "Picture caption must be between 2 and 255 characters") String caption) {
        this.caption = caption;
    }

    public @NotBlank(message = "Picture path is required") String getPath() {
        return path;
    }

    public void setPath(@NotBlank(message = "Picture path is required") String path) {
        this.path = path;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
