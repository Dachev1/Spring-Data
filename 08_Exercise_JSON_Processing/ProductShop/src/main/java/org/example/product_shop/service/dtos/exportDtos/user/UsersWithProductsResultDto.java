package org.example.product_shop.service.dtos.exportDtos.user;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UsersWithProductsResultDto {
    @Expose
    private int usersCount;

    @Expose
    private List<UserWithSoldProductsDto> users;

    public UsersWithProductsResultDto(int usersCount, List<UserWithSoldProductsDto> users) {
        this.usersCount = usersCount;
        this.users = users;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserWithSoldProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSoldProductsDto> users) {
        this.users = users;
    }
}

