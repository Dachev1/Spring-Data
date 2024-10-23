package org.example.car_dealer.data.repositories;

import org.example.car_dealer.data.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {
    List<Customer> findAllByOrderByBirthDateAscIsYoungDriverAsc();

    @Query("SELECT c FROM Customer c JOIN FETCH c.sales s JOIN FETCH s.car")
    List<Customer> findAllWithSales();
}
