package org.jrutten.orderproject.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnusedConvolutedItemRepositoryTest {
    UnusedConvolutedItemRepository itemRepository;

    @BeforeEach
    void setUp(){
        itemRepository = new UnusedConvolutedItemRepository();
    }

    @Test
    void givenAnItemNotYetInStock_addingToStock_OK(){
        Item item = new Item("1", "Apple", "A freaking apple", 1.99, 2);
        itemRepository.addToRepository(item);

        String name = this.itemRepository.getItemMap().get("1").peek().getName();
        assertEquals(item.getName(), name);
    }

    @Test
    void addingTwoSameIDandNamedItemsToStock_oneItemCheaperThanOther_mostExpensiveShouldPopFirst(){
        Item item = new Item("1", "Apple", "A freaking apple", 1.99, 2);
        Item item2 = new Item("1", "Apple", "A freaking apple", 1.70, 2);
        itemRepository.addToRepository(item);
        itemRepository.addToRepository(item2);

        double price = this.itemRepository.getItemMap().get("1").pop().getPrice();
        assertEquals(item.getPrice(), price);

        double price2 = this.itemRepository.getItemMap().get("1").pop().getPrice();
        assertEquals(item2.getPrice(), price2);
    }


    @Test
    void addingTwoSameIDandNamedItemsToStock_oneItemMoreExpensiveThanOther_updateprices(){
        Item item = new Item("1", "Apple", "A freaking apple", 1.99, 2);
        Item item2 = new Item("1", "Apple", "A freaking apple", 2.10, 2);
        itemRepository.addToRepository(item);
        itemRepository.addToRepository(item2);

        Item pop = itemRepository.getItemMap().get("1").pop();

        assertEquals(item2.getPrice(), pop.getPrice());
        assertEquals(4, pop.getStock());
        assertTrue(this.itemRepository.getItemMap().get("1").isEmpty());
    }


    @Test
    void addingTwoSameNamedItemsToStock_sameprice_updateprices(){
        Item item = new Item("1", "Apple", "A freaking apple", 1.99, 2);
        Item item2 = new Item("5", "Apple", "A freaking apple", 1.99, 2);
        itemRepository.addToRepository(item);
        itemRepository.addToRepository(item2);

        Item pop = itemRepository.getItemMap().get("1").pop();

        assertEquals(item2.getPrice(), pop.getPrice());
        assertEquals(4, pop.getStock());
        assertTrue(this.itemRepository.getItemMap().get("1").isEmpty());
    }

    @Test
    void whenAddingFourItems_withPrices6413_thenExpectedPrices643(){
        Item item1 = new Item("1", "Apple", "A freaking apple", 6, 2);
        Item item2 = new Item("1", "Apple", "A freaking apple", 4, 2);
        Item item3 = new Item("1", "Apple", "A freaking apple", 1, 2);
        Item item4 = new Item("1", "Apple", "A freaking apple", 3, 2);

        this.itemRepository.addToRepository(item1);
        this.itemRepository.addToRepository(item2);
        this.itemRepository.addToRepository(item3);
        this.itemRepository.addToRepository(item4);

        ArrayDeque<Item> items = this.itemRepository.getItemMap().get("1");
        List<Item> itemList = items.stream().toList();

        System.out.println(itemList.size());
        itemList.forEach(x -> System.out.println(x.getPrice() + " - " + x.getStock()));

        assertTrue(itemList.size() == 3);


        assertEquals(6, itemList.get(0).getPrice());
        assertEquals(2, itemList.get(0).getStock());

        assertEquals(4, itemList.get(1).getPrice());
        assertEquals(2, itemList.get(1).getStock());

        assertEquals(3, itemList.get(2).getPrice());
        assertEquals(4, itemList.get(2).getStock());

    }

}