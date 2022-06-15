package org.jrutten.orderproject.item;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ItemService {
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemService(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    public ItemDTO addItem(CreateItemDTO createItemDTO) {
        Item item = this.itemMapper.toItem(createItemDTO);
        return this.itemMapper.toItemDTO(this.itemRepository.addToRepository(item));
    }

    public List<ItemDTO> getAll() {
        Collection<Item> items = this.itemRepository.getItemMap().values();
        return this.itemMapper.mapListOfItemsToListOfItemDTO(items);
    }
}
