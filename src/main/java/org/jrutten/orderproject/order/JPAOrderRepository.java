package org.jrutten.orderproject.order;

import org.jrutten.orderproject.order.representations.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JPAOrderRepository extends JpaRepository<Order, Integer> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM orders WHERE fk_customer_id = :id")
    List<Order> findAllByFkCustomerId(@Param("id") int id);

    @Query(nativeQuery = true,
    value = "SELECT * FROM orders where fk_customer_id = :customerId AND id = :orderId")
    Optional<Order> findOrderByCustomerIdAndOrderId(@Param("customerId") int customerId,@Param("orderId") int orderId);
}
