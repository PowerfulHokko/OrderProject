package org.jrutten.orderproject.order.representations;

public class ItemsToOrderDTO {
    private final String itemId;
    private final int requestedAmount;

    public ItemsToOrderDTO(String itemId, int requestedAmount) {
        this.itemId = itemId;
        this.requestedAmount = requestedAmount;
    }

    public String getItemId() {
        return itemId;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }
}
