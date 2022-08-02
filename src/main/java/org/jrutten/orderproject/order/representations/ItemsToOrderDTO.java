package org.jrutten.orderproject.order.representations;

public class ItemsToOrderDTO {
    private final int itemId;
    private final int requestedAmount;

    public ItemsToOrderDTO(int itemId, int requestedAmount) {
        this.itemId = itemId;
        this.requestedAmount = requestedAmount;
    }

    public int getItemId() {
        return itemId;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }
}
