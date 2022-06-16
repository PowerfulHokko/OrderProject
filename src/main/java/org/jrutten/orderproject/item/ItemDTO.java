package org.jrutten.orderproject.item;

public class ItemDTO extends AbstractItem {
    public ItemDTO(String id, String name, String description, double price, int stock) {
        super(id, name, description, price, stock);
    }

    @Override
    public String toString() {
        return "**" +super.getItemId()+ "**";
    }


}
