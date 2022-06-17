package org.jrutten.orderproject.item;

import org.jrutten.orderproject.item.representations.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Item item = new Item("a","Beer", "An apple", 1.20, 5);
        Item item2 = new Item("b","Bacon", "Monkeys love these", 1.20, 5);

        itemRepository.addToRepository(item);
        itemRepository.addToRepository(item2);

        assertTrue(itemRepository.getItemMap().containsKey(item.getItemId()));
        assertTrue(itemRepository.getItemMap().containsKey(item2.getItemId()));
    }

    @Test
    void givenTwoItemsSameNameButDifferentId_whenPost_thenthrowException(){
        int item1Stock = 5;
        int item2Stock = 3;

        Item item = new Item("a","Apple", "An apple", 1.20, item1Stock);
        Item item2 = new Item("ab","Apple", "An apple", 1.80, item2Stock);

        itemRepository.addToRepository(item);

        assertThrows(IllegalArgumentException.class, () -> itemRepository.addToRepository(item2));
    }

    @Test
    void givenNewItemWithIdOfOld_whenInPatchMode_thenOverWrite(){
        Item item = new Item("a","Apple", "An apple", 1.20, 2);
        Item item2 = new Item("a","Ale", "Ale", 2.80, 6);

        this.itemRepository.addToRepository(item);
        this.itemRepository.updateItemInRepository(item2);


        Item itemInMap = this.itemRepository.getItemMap().get(item.getItemId());

        assertEquals(item2.getName(), itemInMap.getName());
        assertEquals(item2.getPrice(), itemInMap.getPrice());
    }

}