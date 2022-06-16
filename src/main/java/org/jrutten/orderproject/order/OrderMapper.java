package org.jrutten.orderproject.order;

import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.item.representations.ItemDTO;
import org.jrutten.orderproject.item.ItemRepository;
import org.jrutten.orderproject.order.representations.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final ItemRepository itemRepository;

    public OrderMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Order toOrder(String customerId, List<ItemsToOrderDTO> orderList, LocalDate shippingDate) {
        List<OrderedItems> items = mapItemsToOrderListToOrderedList(orderList);
        return new Order(customerId, items, shippingDate);
    }

    private List<OrderedItems> mapItemsToOrderListToOrderedList(List<ItemsToOrderDTO> orderList) {
        return orderList.stream()
                .map(item -> new OrderedItems(itemRepository.getItemMap().get(item.getItemId()), itemRepository.getItemMap().get(item.getItemId()).getPrice(), item.getRequestedAmount()))
                .collect(Collectors.toUnmodifiableList());
    }

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getOrderId(),
                order.getCustomerId(),
                this.mapToOrderedItemsDTOList(order.getOrderedItemsList()),
                order.getShippingDate(),
                order.getAmount()
        );
    }

    private List<OrderedItemsDTO> mapToOrderedItemsDTOList(List<OrderedItems> orderedItemsList) {
        return orderedItemsList.stream().map(item -> new OrderedItemsDTO(this.toItemDTO(item.getItem()), item.getPricePerPieceAtRequest(), item.getAmount())).toList();
    }

    private ItemDTO toItemDTO(Item item) {
        return new ItemDTO(item.getItemId(), item.getName(), item.getDescription(), item.getPrice(), item.getStock());
    }

    public List<OrderDTO> listOfOrdersToListOfOrderDto(List<Order> orders) {
        return orders.stream().map(o -> this.toOrderDTO(o)).collect(Collectors.toUnmodifiableList());
    }
}
