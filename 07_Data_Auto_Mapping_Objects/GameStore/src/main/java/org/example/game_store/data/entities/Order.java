package org.example.game_store.data.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    @ElementCollection
    @CollectionTable(name = "orders_games", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "game_title")
    private Set<String> games;

    public Order() {}

    public Order(String fullName, String email, LocalDateTime purchaseDate, Set<String> games) {
        this.fullName = fullName;
        this.email = email;
        this.purchaseDate = purchaseDate;
        this.games = games;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Set<String> getGames() {
        return games;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(fullName, order.fullName) && Objects.equals(email, order.email) && Objects.equals(purchaseDate, order.purchaseDate) && Objects.equals(games, order.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, purchaseDate, games);
    }
}
