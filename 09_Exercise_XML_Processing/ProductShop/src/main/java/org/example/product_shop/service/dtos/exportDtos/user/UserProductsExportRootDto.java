package org.example.product_shop.service.dtos.exportDtos.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserProductsExportRootDto {

    @XmlAttribute(name = "count")
    private int count;

    @XmlElement(name = "user")
    private List<UserWithProductsExportDto> users;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserWithProductsExportDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductsExportDto> users) {
        this.users = users;
    }
}

