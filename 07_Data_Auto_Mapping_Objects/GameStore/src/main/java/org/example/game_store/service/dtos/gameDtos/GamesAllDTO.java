package org.example.game_store.service.dtos.gameDtos;

public class GamesAllDTO {
    private String title;
    private double price;

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", title, price);
    }
}
