package org.jrutten.orderproject.item.representations;

import org.jrutten.orderproject.fieldValidators.FieldValidators;

import java.util.Objects;
import java.util.UUID;

public abstract class AbstractItem {
    private final String itemId;
    private final String name;
    private String description;
    private double price;
    private int stock;

    public AbstractItem(String id, String name, String description, double price, int stock) {
        FieldValidators.guardStringNullAndBlank(name, description);
        FieldValidators.guardZeroOrLessThan((int) price);
        //FieldValidators.guardLessThanZero(stock);

        this.itemId = id == null || id.isBlank() ? UUID.randomUUID().toString() : id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public AbstractItem(String name, String description, double price, int stock){
        this(null, name, description, price, stock);
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        //FieldValidators.guardLessThanZero(stock);
        this.stock = stock;
    }

    public void setPrice(double price) {
        FieldValidators.guardZeroOrLessThan((int) price);
        this.price = price;
    }

    public void setDescription(String description) {
        FieldValidators.guardStringNullAndBlank(description);
        this.description = description;
    }



    @Override
    public String toString() {
        return "AbstractItem{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractItem)) return false;
        AbstractItem that = (AbstractItem) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(itemId, that.itemId) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, description, price);
    }
}

