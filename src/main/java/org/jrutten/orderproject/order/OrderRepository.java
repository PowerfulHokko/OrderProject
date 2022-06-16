package org.jrutten.orderproject.order;

import org.jrutten.orderproject.order.representations.Order;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.logging.Logger;

@Repository
public class OrderRepository {
    private final Logger logger;
    private final Map<String, HashMap<String, Order>> customerOrderMap;

    public OrderRepository() {
        this.logger = Logger.getLogger(this.getClass().getName());
        this.customerOrderMap = new HashMap<>();
    }

    public void placeOrder(Order order) {
        if(this.customerOrderMap.containsKey(order.getCustomerId())){
            checkOverwriteAttemptOnOrderForClient(order);

            this.customerOrderMap.get(order.getCustomerId()).put(order.getOrderId(), order);
        } else {
            this.customerOrderMap.put(order.getCustomerId(), new HashMap<>());
            this.customerOrderMap.get(order.getCustomerId()).put(order.getOrderId(), order);
        }

        logger.info("Succesfully logged order "+order.getOrderId()+" for customer "+order.getCustomerId()+" .");
    }

    private void checkOverwriteAttemptOnOrderForClient(Order order) {
        if(this.customerOrderMap.get(order.getCustomerId()).containsKey(order.getOrderId())){
            throw new IllegalArgumentException("An order with id " + order.getOrderId() + " for customer " + order.getCustomerId() + " already existst and cannot be overwritten");
        }
    }

    public List<Order> getOrdersByCustomerId(String id) {
        if(!this.customerOrderMap.containsKey(id)) return List.of();
        return this.customerOrderMap.get(id).values().stream().toList();
    }

    public Order getOrderById(String id, String order) throws NoSuchCustomerException, OrderNotOfCustomerException{
        HashMap<String, Order> orderHashMap = Optional.ofNullable(this.customerOrderMap.get(id)).orElseThrow(NoSuchCustomerException::new);
        Order order1 = Optional.ofNullable(orderHashMap.get(order)).orElseThrow(OrderNotOfCustomerException::new);
        return Objects.requireNonNull(order1);
    }
}
