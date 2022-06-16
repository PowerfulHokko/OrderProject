package org.jrutten.orderproject.item;

import org.jrutten.orderproject.fieldValidators.FieldValidators;
import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.order.representations.ItemsToOrderDTO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.logging.Logger;

@Repository
public class ItemRepository {
    private final Logger logger;
    private final Map<String, Item> itemMap;

    public ItemRepository() {
        this.logger = Logger.getLogger(this.getClass().getName());
        this.itemMap = new HashMap<>();

        initMap();
    }

    private void initMap() {
        Item item = new Item("Bananas", "Bananas", "Monkeys love these", 2.10, 5);
        Item item2 = new Item("Apples", "Apples", "Pigs love these", 2.40, 5);

        this.itemMap.put(item.getItemId(), item);
        this.itemMap.put(item2.getItemId(), item2);
    }

    public Map<String, Item> getItemMap() {
        return itemMap;
    }


    public Item addToRepository(Item item) {
        FieldValidators.guardStringNullAndBlank(item.getItemId());
        FieldValidators.guardLessThanZero(item.getStock());
        FieldValidators.guardZeroOrLessThan((int) item.getPrice());

        String id = getItemIDFromMapBasedOnIDorNameReturnBlankIfNotFound(item);

        if(!id.equals("")) guardAgainstUnwantedDuplicates(id);

        this.itemMap.put(item.getItemId(), item);
        return this.itemMap.get(item.getItemId());
    }

    private void guardAgainstUnwantedDuplicates(String id) {
            String errorMessage = new StringBuilder()
                    .append("An item named: " + this.itemMap.get(id))
                    .append(" already exists with id: " + id)
                    .append(" -- ")
                    .append("consider using other params or use the patch method to update")
                    .toString();
            throw new IllegalArgumentException(errorMessage);
    }

    private Item updateCurrentItemInMap(Item item, String id) {
        Item itemFromMap = this.itemMap.get(id);
        itemFromMap.setPrice(item.getPrice());
        itemFromMap.setStock(itemFromMap.getStock() + item.getStock());
        itemFromMap.setDescription(item.getDescription());
        return itemFromMap;
    }

    private String getItemIDFromMapBasedOnIDorNameReturnBlankIfNotFound(Item item) {
        String id = "";

        if(this.itemMap.containsKey(item.getItemId())){
            id = item.getItemId();

            if(! this.itemMap.get(id).getName().equals(item.getName())){
                throw new IllegalArgumentException("Same id found but with different name, overwrite not allowed");
            }

        } else if(this.itemMap.values().stream().anyMatch(im -> im.getName().equals(item.getName()))){
            id = this.itemMap.values().stream().filter(im -> im.getName().equals(item.getName())).findFirst().get().getItemId();
            logger.info("Found itemId: " + id);
        } else {
            id = "";
        }
        return id;
    }

    public void removeOrderedItems(List<ItemsToOrderDTO> orderList) {
        orderList.stream().forEach(item -> this.itemMap.get(item.getItemId()).setStock(this.itemMap.get(item.getItemId()).getStock() - item.getRequestedAmount()));
    }

    public Item updateItemInRepository(Item item) {
        if(!this.itemMap.containsKey(item.getItemId())) throw new IllegalArgumentException("No id: " + item.getItemId() + " found");
        this.itemMap.replace(item.getItemId(), item);
        return this.itemMap.get(item.getItemId());
    }
}
