package org.jrutten.orderproject.order;

import lombok.AllArgsConstructor;
import org.jrutten.orderproject.customer.JPACustomerRepository;
import org.jrutten.orderproject.item.JPAItemRepository;
import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.order.representations.ItemsToOrderDTO;
import org.jrutten.orderproject.order.representations.Order;
import org.jrutten.orderproject.order.representations.OrderDTO;
import org.jrutten.orderproject.order.representations.OrderedItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final JPAOrderRepository orderRepository;
    private final JPACustomerRepository customerRepository;
    private final JPAItemRepository itemRepository;
    private final JPAOrderedItemsRepository orderedItemsRepository;

    private final OrderMapper orderMapper;


    public OrderDTO placeOrder(int customerId, List<ItemsToOrderDTO> orderList) {
        //first make and save the order, then add the list of orderedItems and then resave all.

        checkIfOrderListIsPresentAndContainsValues(orderList);
        logger.info("Order request for customer with id " + customerId);
        validateItems(orderList);
        LocalDate shippingDate = getShippingDate(orderList);

        //1 make
        Order order = this.orderMapper.toOrder(customerId, Collections.EMPTY_LIST, shippingDate);
        Order saved = this.orderRepository.save(order);

        List<OrderedItems> collect = orderList.stream().map(itemsToOrderDTO -> {
            Item item = this.itemRepository.getReferenceById(itemsToOrderDTO.getItemId());
            OrderedItems oItem = new OrderedItems(item, itemsToOrderDTO.getRequestedAmount(), item.getPrice());
            OrderedItems savedOrderedItem = this.orderedItemsRepository.save(oItem);
            return savedOrderedItem;
        }).collect(Collectors.toList());

        collect.forEach(c -> c.setFkOrderId(saved.getId()));
        saved.setOrderedItemsList(collect);
        saved.setAmount(saved.getOrderedItemsList().stream().mapToDouble(x -> x.getQuantity()*x.getItem().getPrice()).sum());

        saved.getOrderedItemsList().forEach(update -> {
            update.getItem().setStock(update.getItem().getStock() - update.getQuantity());
            this.itemRepository.save(update.getItem());
        });


        Order save = this.orderRepository.save(saved);
        return this.orderMapper.toOrderDTO(save);
    }

    private void checkIfOrderListIsPresentAndContainsValues(List<ItemsToOrderDTO> orderList) {
        if(orderList == null || orderList.isEmpty()) throw new IllegalArgumentException("No orderlist defined");
    }


    private LocalDate getShippingDate(List<ItemsToOrderDTO> orderList) {
        LocalDate shippingDate = LocalDate.now().plusDays(1);

        if(orderList.stream().anyMatch(i -> this.itemRepository.getReferenceById(i.getItemId()).getStock() < i.getRequestedAmount())){
            shippingDate.plusDays(6);
        }

        return shippingDate;
    }

    private void validateItems(List<ItemsToOrderDTO> orderList) {
        if(orderList.stream().anyMatch(item -> !itemRepository.findById(item.getItemId()).isPresent())){
            throw new NoSuchElementException("Invalid item in basket");
        }

        if(orderList.stream().anyMatch(item -> item.getRequestedAmount() <= 0)){
            throw new NoSuchElementException("Requested amount for items cannot be 0 or less");
        }
    }

    private void validateCustomer(int customerId) {
        if (! this.customerRepository.findById(customerId).isPresent()) {
            throw new NoSuchCustomerException("No client with id" + customerId);
        }
    }

    public List<OrderDTO> getAllByCustomerId(int id) {
        validateCustomer(id);

        List<Order> orders = this.orderRepository.findAllByFkCustomerId(id);

        return this.orderMapper.listOfOrdersToListOfOrderDto(orders);
    }

    public OrderDTO reOrder(int customerId, int orderId) {
        logger.info("reorder request for customer: " + customerId + " and order: " + orderId);
        Order oldOrder = this.orderRepository.findOrderByCustomerIdAndOrderId(customerId, orderId).orElseThrow(OrderNotOfCustomerException::new);
        List<ItemsToOrderDTO> itemsToOrderDTOList = this.orderMapper.remapOrderedItemstoItemsToOrderDTO(oldOrder.getOrderedItemsList());
        OrderDTO newOrder =  this.placeOrder(customerId, itemsToOrderDTOList);
        logger.info(newOrder.getOrderId() + "");

        return newOrder;
    }
}
