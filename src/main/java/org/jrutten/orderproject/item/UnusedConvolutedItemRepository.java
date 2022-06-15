package org.jrutten.orderproject.item;


import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UnusedConvolutedItemRepository {
    private final Logger logger;
    private final Map<String, ArrayDeque<Item>> itemMap;

    public UnusedConvolutedItemRepository() {
        this.logger = Logger.getLogger(this.getClass().getName());
        this.itemMap = new HashMap<>();
    }

    public Map<String, ArrayDeque<Item>> getItemMap() {
        return itemMap;
    }

    /*
        this checks if the item by id or by name is in stock; if not it creates a new register for this item;
        if it is in stock, then it checks if it needs to update the current price, or hold for new price.
        */
    public void addToRepository(Item item) {
        logger.info("Adding to repository: " + item);

        String id = extractIdFromMap(item);

        if(!id.equals("")){
            this.itemMap.put(item.getItemId(), new ArrayDeque<>());
            this.itemMap.get(item.getItemId()).add(item);
        } else {
            ArrayDeque<Item> itemArrayDeque = this.itemMap.get(id);

            if(checkNewPriceHigher(item, itemArrayDeque)){
                updatePriceOfItemInStock(item, itemArrayDeque);
            } else {
                logger.info("New price is lower, pushing to stock ");
                itemArrayDeque.add(item);
            }

        }

    }

    private boolean checkNewPriceHigher(Item item, ArrayDeque<Item> itemArrayDeque) {
        return itemArrayDeque.peekLast().getPrice() <= item.getPrice();
    }

    private void updatePriceOfItemInStock(Item item, ArrayDeque<Item> itemArrayDeque) {
        logger.info("New price is higher or equal, updating for more profit =) ");
        Item pollLast = itemArrayDeque.pollLast();

        pollLast.setPrice(item.getPrice());
        pollLast.setStock(item.getStock() + pollLast.getStock());


        while(!itemArrayDeque.isEmpty() && itemArrayDeque.peekLast().getPrice() <= pollLast.getPrice()){
            Item pollLastTemp = itemArrayDeque.pollLast();
            pollLast.setStock(pollLast.getStock() + pollLast.getStock());
        }

        itemArrayDeque.add(pollLast);
    }

    private String extractIdFromMap(Item item) {
        String id = "";
        //boolean idInMap = false;
        if(this.itemMap.containsKey(item.getItemId())){
            id = item.getItemId();
            //idInMap = true;
            logger.info("found id " + id + " in map");

            checkIfNameIsEqualToExpected(item, id);

        } else if (this.itemMap.values().stream().anyMatch(queue -> queue.stream().anyMatch(itemInStream -> itemInStream.getName().equals(item.getName())))) {
            id = this.itemMap.values().stream().filter(q -> q.stream().anyMatch(i -> i.getName().equals(item.getName()))).findFirst().get().peek().getItemId();
            //idInMap = true;
            logger.info("found name " + item.getName() + " in map with id " + id);
        } else {
            logger.info("no item found with id: " + item.getItemId());
            //idInMap = false;
        }
        return id;
    }

    private void checkIfNameIsEqualToExpected(Item item, String id) {
        if(!this.itemMap.get(id).peek().getName().equals(item.getName())){
            throw new IllegalArgumentException("Id was found, but difference in name, overwrite not allowed");
        }
    }


}

