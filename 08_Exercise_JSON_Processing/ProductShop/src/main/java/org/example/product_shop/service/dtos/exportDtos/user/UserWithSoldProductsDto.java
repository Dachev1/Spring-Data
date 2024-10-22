package org.example.product_shop.service.dtos.exportDtos.user;

import com.google.gson.annotations.Expose;

public class UserWithSoldProductsDto {
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private int age;

    @Expose
    private UserSoldProductsInfoDto soldProducts;

    // Getters and setters
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserSoldProductsInfoDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(UserSoldProductsInfoDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}

