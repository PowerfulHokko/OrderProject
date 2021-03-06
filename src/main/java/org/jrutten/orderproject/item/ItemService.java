package org.jrutten.orderproject.item;

import org.jrutten.orderproject.item.representations.CreateItemDTO;
import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.item.representations.ItemDTO;
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

    public ItemDTO updateItem(String itemid, CreateItemDTO itemDTO) {
        Item item = this.itemMapper.toItem(itemDTO, itemid);
        return this.itemMapper.toItemDTO(this.itemRepository.updateItemInRepository(item));
    }
}
