package org.jrutten.orderproject.item;

import java.util.UUID;

public class Item extends AbstractItem {
    public Item(String id, String name, String description, double price, int stock) {
        super(id, name, description, price, stock);
    }
}
