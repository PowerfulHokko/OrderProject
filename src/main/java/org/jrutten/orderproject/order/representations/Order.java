package org.jrutten.orderproject.order.representations;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final String customerId;
    private final List<OrderedItems> orderedItemsList;
    private final LocalDate shippingDate;
    private final double amount;

    public Order(String orderId, String customerId, List<OrderedItems> orderedItemsList, LocalDate shippingDate, double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderedItemsList = orderedItemsList;
        this.shippingDate = shippingDate;
        this.amount = amount;
    }

    public Order(String customerId, List<OrderedItems> orderList, LocalDate shippingDate) {
        this(generateOrderId(), customerId, orderList, shippingDate, calculateTotalPrice(orderList));
    }

    private static double calculateTotalPrice(List<OrderedItems> orderList) {
        return orderList.stream().mapToDouble(item -> item.getAmount() * item.getPricePerPieceAtRequest()).sum();
    }

    private static String generateOrderId() {
        return UUID.randomUUID().toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderedItems> getOrderedItemsList() {
        return orderedItemsList;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public double getAmount() {
        return amount;
    }
}
