package org.example.product_shop.service.dtos.exportDtos.product;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductExportRootDto {

    @XmlElement(name = "product")
    List<ProductExportDto> productExportDtoList = new ArrayList<>();

    public List<ProductExportDto> getProductExportDtoList() {
        return productExportDtoList;
    }

    public void setProductExportDtoList(List<ProductExportDto> productExportDtoList) {
        this.productExportDtoList = productExportDtoList;
    }
}
