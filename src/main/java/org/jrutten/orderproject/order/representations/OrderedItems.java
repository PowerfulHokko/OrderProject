package org.jrutten.orderproject.order.representations;

import org.jrutten.orderproject.item.representations.Item;

public class OrderedItems {
    private final Item item;
    private final double pricePerPieceAtRequest;
    private int amount;

    public OrderedItems(Item item, double pricePerPieceAtRequest, int amount) {
        this.item = item;
        this.pricePerPieceAtRequest = pricePerPieceAtRequest;
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public double getPricePerPieceAtRequest() {
        return pricePerPieceAtRequest;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
