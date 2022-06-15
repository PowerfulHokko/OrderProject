package org.jrutten.orderproject.item;

import org.springframework.stereotype.Service;

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
        this.itemRepository.addToRepository(item);

        return this.itemMapper.toItemDTO(item);
    }
}
