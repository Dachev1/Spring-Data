package org.example.product_shop.service.dtos.exportDtos.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import org.example.product_shop.service.dtos.exportDtos.product.ProductListExportDto;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductsExportDto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute
    private Integer age;

    @XmlElement(name = "sold-products")
    private ProductListExportDto soldProducts;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductListExportDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductListExportDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}

