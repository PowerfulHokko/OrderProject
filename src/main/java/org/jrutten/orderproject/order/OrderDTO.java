package org.jrutten.orderproject.order;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class OrderDTO {
    private final String orderId;
    private final String customerId;
    private final List<OrderedItemsDTO> orderedItemsList;
    private final LocalDate shippingDate;
    private final double amountToPay;

    public OrderDTO(String orderId, String customerId, List<OrderedItemsDTO> orderedItemsList, LocalDate shippingDate, double amountToPay) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderedItemsList = orderedItemsList;
        this.shippingDate = shippingDate;
        this.amountToPay = amountToPay;
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

    public List<OrderedItemsDTO> getOrderedItemsList() {
        return orderedItemsList;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public double getAmountToPay() {
        return amountToPay;
    }
}
