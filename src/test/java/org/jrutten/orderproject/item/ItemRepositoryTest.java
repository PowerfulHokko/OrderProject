package org.jrutten.orderproject.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository;

    @BeforeEach
    void setUp(){
        itemRepository = new ItemRepository();
    }

    @Test
    void givenNoItemsInStock_whenAddingAnItem_ok(){
        Item item = new Item("a","Apple", "An apple", 1.20, 5);

        itemRepository.addToRepository(item);

        assertTrue(itemRepository.getItemMap().containsKey(item.getItemId()));
    }

    @Test
    void givenTwoDifferentItems_whenAddingItems_ok(){
        Item item = new Item("a","Apple", "An apple", 1.20, 5);
        Item item2 = new Item("b","Bananas", "Monkeys love these", 1.20, 5);

        itemRepository.addToRepository(item);
        itemRepository.addToRepository(item2);

        assertTrue(itemRepository.getItemMap().containsKey(item.getItemId()));
        assertTrue(itemRepository.getItemMap().containsKey(item2.getItemId()));
    }

    @Test
    void givenTwoItemsSameNameButDifferentId_thenUpdateFirst(){
        int item1Stock = 5;
        int item2Stock = 3;

        Item item = new Item("a","Apple", "An apple", 1.20, item1Stock);
        Item item2 = new Item("ab","Apple", "An apple", 1.80, item2Stock);

        itemRepository.addToRepository(item);
        itemRepository.addToRepository(item2);

        assertTrue(this.itemRepository.getItemMap().containsKey(item.getItemId()));
        assertEquals(item2.getPrice(), this.itemRepository.getItemMap().get(item.getItemId()).getPrice());        assertEquals(item2.getPrice(), this.itemRepository.getItemMap().get(item.getItemId()).getPrice());
        assertEquals((item1Stock + item2Stock), this.itemRepository.getItemMap().get(item.getItemId()).getStock());

    }

}