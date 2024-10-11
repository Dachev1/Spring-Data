package org.example.user_system.data.entities;

import jakarta.persistence.*;
import org.example.user_system.data.entities.base_entity.BaseEntity;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "is_public")
    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    @ManyToMany(mappedBy = "albums")
    private Set<Picture> pictures = new HashSet<>();

    public Album() {}

    public Album(String name, String backgroundColor, Boolean isPublic) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.isPublic = isPublic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }
}
