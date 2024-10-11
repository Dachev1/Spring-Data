package org.example.user_system.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.example.user_system.data.entities.base_entity.BaseEntity;
import org.example.user_system.data.entities.anotation.Password;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(length = 30, nullable = false)
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 30)
    @NotNull
    private String username;

    @Column(nullable = false)
    @Password
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "registered_on")
    private LocalDateTime registeredOn;

    @Column(name = "last_time_logged_in ")
    private LocalDateTime lastTimeLoggedIn;

    @Size(min = 1, max = 120)
    private int age;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne(optional = false)
    @JoinColumn(name = "born_town_id", referencedColumnName = "id")
    private Town bornTown;

    @ManyToOne(optional = false)
    @JoinColumn(name = "current_town_id", referencedColumnName = "id")
    private Town currentTown;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();

    @ManyToMany(mappedBy = "friends")
    private Set<User> friendsOf = new HashSet<>();

    @Transient
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public User() {}

    public User(String firstName, String lastName,String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
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

    public @NotBlank(message = "Username is required") @Size(min = 4, max = 30) @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") @Size(min = 4, max = 30) @NotNull String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    @Size(min = 1, max = 120)
    public int getAge() {
        return age;
    }

    public void setAge(@Size(min = 1, max = 120) int age) {
        this.age = age;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    public Town getCurrentTown() {
        return currentTown;
    }

    public void setCurrentTown(Town currentTown) {
        this.currentTown = currentTown;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<User> getFriendsOf() {
        return friendsOf;
    }

    public void setFriendsOf(Set<User> friendsOf) {
        this.friendsOf = friendsOf;
    }
}
