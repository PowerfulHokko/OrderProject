package org.jrutten.orderproject.item;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public Item toItem(CreateItemDTO createItemDTO) {
        return new Item(createItemDTO.getItemId(), createItemDTO.getName(), createItemDTO.getDescription(), createItemDTO.getPrice(), createItemDTO.getStock());
    }

    public ItemDTO toItemDTO(Item item) {
        return new ItemDTO(item.getItemId(), item.getName(), item.getDescription(), item.getPrice(), item.getStock());
    }

    public List<ItemDTO> mapListOfItemsToListOfItemDTO(Collection<Item> items) {
        return items.stream().map(item -> this.toItemDTO(item)).collect(Collectors.toUnmodifiableList());
    }
}
