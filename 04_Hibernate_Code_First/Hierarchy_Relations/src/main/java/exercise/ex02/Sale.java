package exercise.ex02;

import exercise.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_location_id", nullable = false)
    private StoreLocation storeLocation;

    @Column(nullable = false)
    private LocalDate date;

    public Sale() {
    }
}
