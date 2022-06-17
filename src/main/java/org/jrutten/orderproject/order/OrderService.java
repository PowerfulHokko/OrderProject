package org.jrutten.orderproject.order;

import org.jrutten.orderproject.customer.CustomerRepository;
import org.jrutten.orderproject.fieldValidators.FieldValidators;
import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.item.ItemRepository;
import org.jrutten.orderproject.order.representations.ItemsToOrderDTO;
import org.jrutten.orderproject.order.representations.Order;
import org.jrutten.orderproject.order.representations.OrderDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class OrderService {
    private final Logger logger;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ItemRepository itemRepository, OrderMapper orderMapper) {
        this.logger = Logger.getLogger(this.getClass().getName());
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDTO placeOrder(String customerId, List<ItemsToOrderDTO> orderList) {
        checkIfOrderListIsPresentAndContainsValues(orderList);
        logger.info("Order request for " + customerId);
        validateCustomer(customerId);
        validateItems(orderList);

        LocalDate shippingDate = getShippingDate(orderList);

        Order order = this.orderMapper.toOrder(customerId, orderList, shippingDate);
        this.itemRepository.removeOrderedItems(orderList);
        this.orderRepository.placeOrder(order);

        return this.orderMapper.toOrderDTO(order);
    }

    private void checkIfOrderListIsPresentAndContainsValues(List<ItemsToOrderDTO> orderList) {
        if(orderList == null || orderList.isEmpty()) throw new IllegalArgumentException("No orderlist defined");
    }


    private LocalDate getShippingDate(List<ItemsToOrderDTO> orderList) {
        LocalDate shippingDate = LocalDate.now().plusDays(1);

        if(orderList.stream().anyMatch(i -> this.itemRepository.getItemMap().get(i.getItemId()).getStock() < i.getRequestedAmount())){
            shippingDate.plusDays(6);
        }

        return shippingDate;
    }

    private void validateItems(List<ItemsToOrderDTO> orderList) {
        Map<String, Item> itemMap = this.itemRepository.getItemMap();
        if(orderList.stream().anyMatch(item -> !itemMap.containsKey(item.getItemId()))){
            throw new NoSuchElementException("Invalid item in basket");
        }

        if(orderList.stream().anyMatch(item -> item.getRequestedAmount() <= 0)){
            throw new NoSuchElementException("Requested amount for items cannot be 0 or less");
        }
    }

    private void validateCustomer(String customerId) {
        if (! this.customerRepository.getCustomerMap().containsKey(customerId)) {
            throw new NoSuchCustomerException("No client with id" + customerId);
        }
    }

    public List<OrderDTO> getAllByCustomerId(String id) {
        FieldValidators.guardStringNullAndBlank(id);
        validateCustomer(id);

        List<Order> orders = this.orderRepository.getOrdersByCustomerId(id);

        return this.orderMapper.listOfOrdersToListOfOrderDto(orders);
    }

    public OrderDTO reOrder(String id, String order) {
        logger.info("reorder request for customer: " + id + " and order: " + order);
        Order oldOrder = this.orderRepository.getOrderById(id, order);
        List<ItemsToOrderDTO> itemsToOrderDTOList = this.orderMapper.remapOrderedItemstoItemsToOrderDTO(oldOrder.getOrderedItemsList());
        return this.placeOrder(id, itemsToOrderDTOList);
    }
}
