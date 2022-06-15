package org.jrutten.orderproject.item;

import org.jrutten.orderproject.fieldValidators.FieldValidators;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class ItemRepository {
    private final Logger logger;
    private final Map<String, Item> itemMap;

    public ItemRepository() {
        this.logger = Logger.getLogger(this.getClass().getName());
        this.itemMap = new HashMap<>();
    }

    public Map<String, Item> getItemMap() {
        return itemMap;
    }


    public void addToRepository(Item item) {
        FieldValidators.guardStringNullAndBlank(item.getItemId());
        FieldValidators.guardLessThanZero(item.getStock());
        FieldValidators.guardZeroOrLessThan((int) item.getPrice());
        String id = getItemIDFromMapBasedOnIDorNameReturnBlankIfNotFound(item);

        if(!id.equals("")){
            updateCurrentItemInMap(item, id);
        } else {
            this.itemMap.put(item.getItemId(), item);
        }


    }

    private void updateCurrentItemInMap(Item item, String id) {
        Item itemFromMap = this.itemMap.get(id);
        itemFromMap.setPrice(item.getPrice());
        itemFromMap.setStock(itemFromMap.getStock() + item.getStock());
        itemFromMap.setDescription(item.getDescription());
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
        } else {
            id = "";
        }
        return id;
    }

}
