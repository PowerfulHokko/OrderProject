package org.jrutten.orderproject.order.representations;

import org.jrutten.orderproject.fieldValidators.FieldValidators;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class OrderDTO {
    private final String orderId;
    private final String customerId;
    private final List<OrderedItemsDTO> orderedItemsList;
    private final LocalDate shippingDate;
    private final double amountToPay;

    public OrderDTO(String orderId, String customerId, List<OrderedItemsDTO> orderedItemsList, LocalDate shippingDate, double amountToPay) {
        FieldValidators.guardZeroOrLessThan((int) amountToPay);
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
        return Collections.unmodifiableList(orderedItemsList);
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public double getAmountToPay() {
        return amountToPay;
    }


    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderedItemsList=" + orderedItemsList +
                ", shippingDate=" + shippingDate +
                ", amountToPay=" + amountToPay +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Double.compare(orderDTO.amountToPay, amountToPay) == 0 && Objects.equals(orderId, orderDTO.orderId) && Objects.equals(customerId, orderDTO.customerId) && OrderedItemsDTO.checkIfListsAreEquals(orderedItemsList, orderDTO.orderedItemsList) && Objects.equals(shippingDate, orderDTO.shippingDate);
    }

//    public static boolean checkIfListsAreEquals(List<OrderedItemsDTO> orderedItemsList, List<OrderedItemsDTO> orderedItemsList1) {
//        if(orderedItemsList.size() != orderedItemsList1.size()) return false;
//        return orderedItemsList.stream().noneMatch(order -> orderedItemsList1.stream().noneMatch(order2 -> order2.equals(order)));
//    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, orderedItemsList, shippingDate, amountToPay);
    }
}
