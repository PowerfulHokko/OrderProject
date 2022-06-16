package org.jrutten.orderproject.item.representations;

public class CreateItemDTO extends AbstractItem {
    public CreateItemDTO(String name, String description, double price, int stock) {
        super(name, description, price, stock);
    }
}
