package org.jrutten.orderproject.order.representations;

import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.item.representations.ItemDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDTOTest {

    @Test
    void givenAllFieldsFilled_whenCreatingANewOrderDTO_thenOK(){
        ItemDTO item = new ItemDTO("item", "item", "an item", 100, 50);
        List<OrderedItemsDTO> OrderedItems = List.of(new OrderedItemsDTO(item, 40, 1));
        OrderDTO orderDTO = new OrderDTO("a", "b", OrderedItems, LocalDate.now(), 200);

        assertTrue(orderDTO.getOrderedItemsList().get(0).getItem().equals(item));
    }

    @Test
    void givenAmountToPayForSomeReasonIsZero_whenCreatingANewOrderDTO_throwIllegalArgumentException(){
        ItemDTO item = new ItemDTO("item", "item", "an item", 100, 50);
        List<OrderedItemsDTO> OrderedItems = List.of(new OrderedItemsDTO(item, 40, 1));
        assertThrows(IllegalArgumentException.class, () -> new OrderDTO("a", "b", OrderedItems, LocalDate.now(), 0));
    }

    @Test
    void givenTwoListsOfOrderedItemsDTO_whenBothListsContainTheSameItemsExceptForPrice_returnTrue() {
        ItemDTO item1 = new ItemDTO("item1", "item1", "an item", 50, 30);
        ItemDTO item2 = new ItemDTO("item2", "item2", "an item", 90, 70);
        OrderedItemsDTO o1 = new OrderedItemsDTO(item1, 5, 8);
        OrderedItemsDTO o2 = new OrderedItemsDTO(item2, 10, 16);


        ItemDTO item1b = new ItemDTO("item1", "item1", "an item", 30, 50);
        ItemDTO item2b = new ItemDTO("item2", "item2", "an item", 105, 20);
        OrderedItemsDTO o1b = new OrderedItemsDTO(item1b, 6, 8);
        OrderedItemsDTO o2b = new OrderedItemsDTO(item2b, 11, 16);

        List<OrderedItemsDTO> list1 = List.of(o1, o2);
        List<OrderedItemsDTO> list2 = List.of(o1b, o2b);

        assertTrue(OrderedItemsDTO.checkIfListsAreEquals(list1, list2));
    }

    @Test
    void givenTwoListsOfOrderedItemsDTO_whenBothListsDONOTContainTheSameItemsExceptForPrice_returnFalse() {
        ItemDTO item1 = new ItemDTO("item1", "item1", "an item", 50, 30);
        ItemDTO item2 = new ItemDTO("item2", "item2", "an item", 90, 70);
        OrderedItemsDTO o1 = new OrderedItemsDTO(item1, 5, 8);
        OrderedItemsDTO o2 = new OrderedItemsDTO(item2, 10, 16);


        ItemDTO item1b = new ItemDTO("item1", "item1", "an item", 30, 50);
        OrderedItemsDTO o1b = new OrderedItemsDTO(item1b, 6, 9);

        List<OrderedItemsDTO> list1 = List.of(o1, o2);
        List<OrderedItemsDTO> list2 = List.of(o1b);

        assertFalse(OrderedItemsDTO.checkIfListsAreEquals(list1, list2));
    }
}