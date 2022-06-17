package org.jrutten.orderproject.order.representations;

import org.jrutten.orderproject.fieldValidators.FieldValidators;
import org.jrutten.orderproject.item.representations.ItemDTO;

import java.util.Objects;

public class OrderedItemsDTO{
    private final ItemDTO item;
    private final double pricePerPieceAtRequest;
    private int amount;

    public OrderedItemsDTO(ItemDTO item, double pricePerPieceAtRequest, int amount) {
        FieldValidators.guardZeroOrLessThan(amount, (int) pricePerPieceAtRequest);
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
        FieldValidators.guardZeroOrLessThan(amount);
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "**" +this.getItem().getItemId()+ "**"+this.getAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderedItemsDTO)) return false;
        OrderedItemsDTO that = (OrderedItemsDTO) o;
        return Double.compare(that.pricePerPieceAtRequest, pricePerPieceAtRequest) == 0 && amount == that.amount && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, pricePerPieceAtRequest, amount);
    }
}
