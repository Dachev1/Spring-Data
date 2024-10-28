package org.example.product_shop.service.dtos.exportDtos.category;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryWithProductStatsRootDto {

    @XmlElement(name = "category")
    private List<CategoryWithProductStatsDto> categories;

    public List<CategoryWithProductStatsDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryWithProductStatsDto> categories) {
        this.categories = categories;
    }
}
