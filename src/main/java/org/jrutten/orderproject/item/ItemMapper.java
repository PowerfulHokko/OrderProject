package org.jrutten.orderproject.item;

import org.jrutten.orderproject.item.representations.CreateItemDTO;
import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.item.representations.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public Item toItem(CreateItemDTO createItemDTO) {
        return new Item(createItemDTO.getName(), createItemDTO.getDescription(), createItemDTO.getPrice(), createItemDTO.getStock());
    }

    public Item toItem(CreateItemDTO createItemDTO, int idForUpdate) {
        return new Item(idForUpdate, createItemDTO.getName(), createItemDTO.getDescription(), createItemDTO.getPrice(), createItemDTO.getStock());
    }

    public ItemDTO toItemDTO(Item item) {
        return new ItemDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getStock());
    }

    public List<ItemDTO> mapListOfItemsToListOfItemDTO(Collection<Item> items) {
        return items.stream().map(item -> this.toItemDTO(item)).collect(Collectors.toUnmodifiableList());
    }
}
