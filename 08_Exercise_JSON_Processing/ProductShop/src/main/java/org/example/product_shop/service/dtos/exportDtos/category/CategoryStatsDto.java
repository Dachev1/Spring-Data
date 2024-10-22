package org.example.product_shop.service.dtos.exportDtos.category;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class CategoryStatsDto {
    @Expose
    private String category;

    @Expose
    private long productCount;

    @Expose
    private BigDecimal averagePrice;

    @Expose
    private BigDecimal totalRevenue;

    public CategoryStatsDto(String name, long productCount, BigDecimal averagePrice, BigDecimal totalRevenue) {
        this.category = name;
        this.productCount = productCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }

    public String getCategoryName() {
        return category;
    }

    public void setCategoryName(String categoryName) {
        this.category = categoryName;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}