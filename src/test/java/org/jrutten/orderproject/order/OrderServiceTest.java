package org.jrutten.orderproject.order;

import org.jrutten.orderproject.customer.Address;
import org.jrutten.orderproject.customer.Customer;
import org.jrutten.orderproject.customer.CustomerRepository;
import org.jrutten.orderproject.item.Item;
import org.jrutten.orderproject.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    OrderService orderService;

    OrderRepository orderRepository;
    CustomerRepository customerRepository;
    ItemRepository itemRepository;
    OrderMapper orderMapper;

    @BeforeEach
    void setUp(){
        this.orderRepository = new OrderRepository();
        this.customerRepository = new CustomerRepository();
        this.itemRepository = new ItemRepository();
        this.orderMapper = new OrderMapper(this.itemRepository);
        this.orderService = new OrderService(orderRepository, customerRepository, itemRepository, orderMapper);
    }

    @Test
    void givenAValidOrder_whenPlacingOrder_thenSubtractItemsFromItemRepositoryAndReturnOrder(){
        Item item = new Item("1", "bike", "2 wheels", 20, 2);
        Address address = new Address("lane",3,"b", 3000, "L-city");
        Customer customer = new Customer("az", "Alfred", "Jab", "af@jad.be", address, "044-5555-77");

        this.itemRepository.addToRepository(item);
        this.customerRepository.registerCustomerAccount(customer);

        List<ItemsToOrderDTO> items = new ArrayList<>();
        items.add(new ItemsToOrderDTO("1", 1));

        OrderDTO orderDTO = this.orderService.placeOrder(customer.getId(), items);

        assertEquals(1, orderDTO.getOrderedItemsList().get(0).getAmount());
        assertEquals(1, this.itemRepository.getItemMap().get(item.getItemId()).getStock());
        assertTrue(orderDTO.getCustomerId().equals(customer.getId()));
    }





}