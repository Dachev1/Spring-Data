package org.example.product_shop.service.dtos.exportDtos.user;

import com.google.gson.annotations.Expose;
import org.example.product_shop.service.dtos.exportDtos.product.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class UserSoldProductsInfoDto {
    @Expose
    private int count;

    @Expose
    private List<ProductDto> products;

    public UserSoldProductsInfoDto() {
        this.products = new ArrayList<>();
    }

    public UserSoldProductsInfoDto(int count, List<ProductDto> products) {
        this.count = count;
        this.products = (products != null) ? products : new ArrayList<>();
    }

    // Getters and setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}

