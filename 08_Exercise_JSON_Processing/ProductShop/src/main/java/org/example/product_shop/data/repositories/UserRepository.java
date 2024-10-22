package org.example.product_shop.data.repositories;

import org.example.product_shop.data.entities.User;
import org.example.product_shop.service.dtos.exportDtos.user.UserSoldProductsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u " +
            "JOIN u.soldProducts p " +
            "JOIN p.buyer b " +
            "WHERE p.buyer IS NOT NULL " +
            "GROUP BY u " +
            "ORDER BY SIZE(u.soldProducts) DESC, u.lastName ASC")
    List<User> findAllUsersWithSoldProducts();
}
