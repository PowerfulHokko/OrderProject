package org.jrutten.orderproject.item;

import org.jrutten.orderproject.item.representations.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAItemRepository extends JpaRepository<Item, Integer> {
}
