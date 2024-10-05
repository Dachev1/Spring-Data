package exercise.ex02;

import exercise.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @OneToMany(mappedBy = "product")
    private Set<Sale> sales;

    public Product() {}
}
