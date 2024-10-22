package org.example.product_shop.data.repositories;

import org.example.product_shop.data.entities.Category;
import org.example.product_shop.service.dtos.exportDtos.category.CategoryStatsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
