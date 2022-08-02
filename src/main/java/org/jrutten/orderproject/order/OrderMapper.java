package org.jrutten.orderproject.order;

import lombok.AllArgsConstructor;
import org.jrutten.orderproject.item.JPAItemRepository;
import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.item.representations.ItemDTO;
import org.jrutten.orderproject.order.representations.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final JPAItemRepository itemRepository;

    public Order toOrder(int customerId, List<ItemsToOrderDTO> orderList, LocalDate shippingDate) {
        List<OrderedItems> items = mapItemsToOrderListToOrderedList(0, orderList);
        Order order =  new Order(customerId, items, shippingDate);
        setFkOrderId(items, order);
        return order;
    }

    public void setFkOrderId(List<OrderedItems> items, Order order) {
        items.stream().forEach(i -> i.setFkOrderId(order.getId()));
    }

    private List<OrderedItems> mapItemsToOrderListToOrderedList(int orderId, List<ItemsToOrderDTO> orderList) {
        return orderList.stream().map(itemsToOrderDTO -> {
            Optional<Item> byId = itemRepository.findById(itemsToOrderDTO.getItemId());
            Item item = byId.orElseThrow(NoSuchElementException::new);

            return new OrderedItems(orderId, item, itemsToOrderDTO.getRequestedAmount(), item.getPrice());
        }).collect(Collectors.toList());
    }

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getCustomerId(),
                this.mapToOrderedItemsDTOList(order.getOrderedItemsList()),
                order.getShippingDate(),
                order.getAmount()
        );
    }

    private List<OrderedItemsDTO> mapToOrderedItemsDTOList(List<OrderedItems> orderedItemsList) {
        return orderedItemsList.stream().map(item -> new OrderedItemsDTO(this.toItemDTO(item.getItem()), item.getCostPerPiece(), item.getQuantity())).toList();
    }

    private ItemDTO toItemDTO(Item item) {
        return new ItemDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getStock());
    }

    public List<OrderDTO> listOfOrdersToListOfOrderDto(List<Order> orders) {
        return orders.stream().map(o -> this.toOrderDTO(o)).collect(Collectors.toUnmodifiableList());
    }

    public List<ItemsToOrderDTO> remapOrderedItemstoItemsToOrderDTO(List<OrderedItems> orderedItemsList) {
        return orderedItemsList.stream().map(item -> new ItemsToOrderDTO(item.getItem().getId(), item.getQuantity())).collect(Collectors.toUnmodifiableList());
    }

    public List<OrderedItems> mapToOrderedItems(int id, List<ItemsToOrderDTO> orderList) {
        return orderList.stream().map(i -> {
            Item item = this.itemRepository.findById(i.getItemId()).orElseThrow(NoSuchElementException::new);
            return new OrderedItems(id, item, i.getRequestedAmount(), item.getPrice());
        }).collect(Collectors.toList());
    }
}
