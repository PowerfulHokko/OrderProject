package org.jrutten.orderproject.order;

import org.jrutten.orderproject.order.representations.OrderedItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface JPAOrderedItemsRepository extends JpaRepository<OrderedItems, Integer> {
}
