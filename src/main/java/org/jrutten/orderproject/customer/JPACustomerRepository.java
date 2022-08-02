package org.jrutten.orderproject.customer;

import org.jrutten.orderproject.customer.representations.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JPACustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM customers WHERE email = :email ;")
    Optional<Customer> findByEmail(@Param("email") String email);
}
