package org.jrutten.orderproject.order;

import org.jrutten.orderproject.item.Item;
import org.springframework.core.annotation.Order;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class AbstractOrder {
//    private final String customerId;
    private String orderID;
    private List<OrderedItem> orderedItemsList;
    private Date shippindDate;
    private double totalPrice;

    AbstractOrder(String customerId, List<OrderedItem> items){
//        this.customerId = customerId;
        this.orderID = UUID.randomUUID().toString();
//        this.orderedItemsList =
    }

}

