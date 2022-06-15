package org.jrutten.orderproject.order;

import org.jrutten.orderproject.item.Item;

public class OrderedItem {
    private final Item item;
    private final String itemId;
    private final String itemName;
    private final double pricePerPieceAtOrderTime;
    private int amount;

    OrderedItem(Item item, int amount) {
        this.item = item;
        this.amount = amount;

        double temp = item.getPrice();
        this.pricePerPieceAtOrderTime = temp;

        this.itemId = item.getItemId();
        this.itemName = item.getName();
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPricePerPieceAtOrderTime() {
        return pricePerPieceAtOrderTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
