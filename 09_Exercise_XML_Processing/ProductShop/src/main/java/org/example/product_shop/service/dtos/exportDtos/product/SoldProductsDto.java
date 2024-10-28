package org.example.product_shop.service.dtos.exportDtos.product;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsDto {

    @XmlElement(name = "product")
    private List<SoldProductDto> products;

    public List<SoldProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<SoldProductDto> products) {
        this.products = products;
    }
}

