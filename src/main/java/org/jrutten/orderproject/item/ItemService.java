package org.jrutten.orderproject.item;

import lombok.AllArgsConstructor;
import org.jrutten.orderproject.item.representations.CreateItemDTO;
import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.item.representations.ItemDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ItemService {
    private final ItemMapper itemMapper;
    private final JPAItemRepository itemRepository;



    public ItemDTO addItem(CreateItemDTO createItemDTO) {
        Item item = this.itemMapper.toItem(createItemDTO);
        return this.itemMapper.toItemDTO(this.itemRepository.save(item));
    }

    public List<ItemDTO> getAll() {
        Collection<Item> items = this.itemRepository.findAll();
        return this.itemMapper.mapListOfItemsToListOfItemDTO(items);
    }

    public ItemDTO updateItem(int itemid, CreateItemDTO itemDTO) {
        Item item = this.itemMapper.toItem(itemDTO, itemid);
        return this.itemMapper.toItemDTO(this.itemRepository.save(item));
    }
}
