package org.jrutten.orderproject.item;

import org.jrutten.orderproject.item.representations.CreateItemDTO;
import org.jrutten.orderproject.item.representations.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {
    ItemService itemService;
    ItemRepository itemRepository;
    ItemMapper itemMapper;

    @BeforeEach
    void setUp(){
        this.itemMapper = new ItemMapper();
        this.itemRepository = new ItemRepository();
        this.itemService = new ItemService(itemMapper, itemRepository);
    }

    @Test
    void givenANewItem_whenAddingNewItem_thenOK(){
        CreateItemDTO newItem = new CreateItemDTO("Table", "Flat white designer table", 210.99, 3);

        ItemDTO itemDTO = this.itemService.addItem(newItem);

        assertEquals(newItem.getName(), itemDTO.getName());
        assertEquals(newItem.getDescription(), itemDTO.getDescription());
        assertEquals(newItem.getPrice(), itemDTO.getPrice());
        assertEquals(newItem.getStock(), itemDTO.getStock());
    }

    @Test
    void givenANewItemButWithSameNameAsOldWhenCreatingThenThrowException(){
        CreateItemDTO newItem = new CreateItemDTO("Table", "Flat white designer table", 210.99, 3);
        CreateItemDTO newItem2 = new CreateItemDTO("Table", "Flat black designer table", 240.99, 1);

        this.itemService.addItem(newItem);

        assertThrows(IllegalArgumentException.class, () -> this.itemService.addItem(newItem2));
    }

    @Test
    void givenNewItemForExistingId_whenPatch_thenOK(){
        CreateItemDTO newItem = new CreateItemDTO("Table", "Flat white designer table", 210.99, 3);
        CreateItemDTO newItem2 = new CreateItemDTO("Table", "Flat black designer table", 240.99, 1);

        this.itemService.addItem(newItem);
        ItemDTO itemDTO = this.itemService.updateItem(newItem.getItemId(), newItem2);

        assertEquals(newItem2.getName(), itemDTO.getName());
        assertEquals(newItem2.getDescription(), itemDTO.getDescription());
        assertEquals(newItem2.getPrice(), itemDTO.getPrice());
        assertEquals(newItem2.getStock(), itemDTO.getStock());
    }

    @Test
    void givenANewItemForNonExistentId_whenPatch_throwException(){
        CreateItemDTO newItem = new CreateItemDTO("Table", "Flat white designer table", 210.99, 3);
        assertThrows(NoSuchElementException.class, () -> this.itemService.updateItem("absoluteboggerid", newItem));
    }

    @Test
    void givenARepositoryWithItems_whenCallingGetAll_returnsListOfItemDTO(){
        assertNotEquals(0, this.itemService.getAll().size());
    }

}