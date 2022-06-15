package org.jrutten.orderproject.order;

import org.jrutten.orderproject.item.Item;
import org.jrutten.orderproject.item.ItemDTO;

public class OrderedItemsDTO{
    private final ItemDTO item;
    private final double pricePerPieceAtRequest;
    private int amount;

    public OrderedItemsDTO(ItemDTO item, double pricePerPieceAtRequest, int amount) {
        this.item = item;
        this.pricePerPieceAtRequest = pricePerPieceAtRequest;
        this.amount = amount;
    }

    public ItemDTO getItem() {
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
