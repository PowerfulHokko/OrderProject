package org.jrutten.orderproject.item;

public class CreateItemDTO extends AbstractItem {
    public CreateItemDTO(String name, String description, double price, int stock) {
        super(name, description, price, stock);
    }
}
