package org.example.product_shop.service.dtos.exportDtos.product;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductListExportDto {

    @XmlAttribute(name = "count")
    private int count;

    @XmlElement(name = "product")
    private List<SoldProductExportDto> products;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SoldProductExportDto> getProducts() {
        return products;
    }

    public void setProducts(List<SoldProductExportDto> products) {
        this.products = products;
    }
}

