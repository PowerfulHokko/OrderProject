package org.jrutten.orderproject.order;

import org.jrutten.orderproject.customer.representations.Address;
import org.jrutten.orderproject.customer.representations.Customer;
import org.jrutten.orderproject.customer.CustomerRepository;
import org.jrutten.orderproject.item.representations.Item;
import org.jrutten.orderproject.item.ItemRepository;
import org.jrutten.orderproject.order.representations.ItemsToOrderDTO;
import org.jrutten.orderproject.order.representations.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Test
    void givenACustomerWithNoOrders_whenGettingTheOrders_returnEmptyList(){
        Address address = new Address("lane",3,"b", 3000, "L-city");
        Customer customer = new Customer("ID123", "Jason", "Bourne", "jb@cia.gov", address, "555");
        this.customerRepository.registerCustomerAccount(customer);

        List<OrderDTO> allByCustomerId = this.orderService.getAllByCustomerId(customer.getId());

        assertTrue(allByCustomerId.size() == 0);
    }

    @Test
    void givenACustomerWithSomeOrders_whenGettingTheOrders_returnListOfTheseOrders(){
        Address address = new Address("lane",3,"b", 3000, "L-city");
        Customer customer = new Customer("ID123", "Jason", "Bourne", "jb@cia.gov", address, "555");
        this.customerRepository.registerCustomerAccount(customer);

        Item item1 = new Item("sc55", "item1", "2 wheels", 20, 2);
        Item item2 = new Item("sc66", "item2", "2 wheels", 20, 2);
        Item item3 = new Item("sc77", "item3", "2 wheels", 20, 4);
        this.itemRepository.addToRepository(item1);
         this.itemRepository.addToRepository(item2);
         this.itemRepository.addToRepository(item3);

        List<ItemsToOrderDTO> itemsForOrder1 = new ArrayList<>();
        itemsForOrder1.add(new ItemsToOrderDTO("sc55", 1));

        List<ItemsToOrderDTO> itemsForOrder2 = new ArrayList<>();
        itemsForOrder2.add(new ItemsToOrderDTO("sc66", 1));
        itemsForOrder2.add(new ItemsToOrderDTO("sc77", 2));

        List<ItemsToOrderDTO> itemsForOrder3 = new ArrayList<>();
        itemsForOrder3.add(new ItemsToOrderDTO("sc55", 1));
        itemsForOrder3.add(new ItemsToOrderDTO("sc66", 1));
        itemsForOrder3.add(new ItemsToOrderDTO("sc77", 2));

        OrderDTO orderDTO1 = this.orderService.placeOrder(customer.getId(), itemsForOrder1);
        OrderDTO orderDTO2 = this.orderService.placeOrder(customer.getId(), itemsForOrder2);
        OrderDTO orderDTO3 = this.orderService.placeOrder(customer.getId(), itemsForOrder3);

        List<OrderDTO> allByCustomerId = this.orderService.getAllByCustomerId(customer.getId());

        boolean checkOrder1 = allByCustomerId.stream().anyMatch(order -> order.equals(orderDTO1));
        boolean checkOrder2 = allByCustomerId.stream().anyMatch(order -> order.equals(orderDTO2));
        boolean checkOrder3 = allByCustomerId.stream().anyMatch(order -> order.equals(orderDTO3));

        assertTrue(checkOrder1);
        assertTrue(checkOrder2);
        assertTrue(checkOrder3);

    }

    @Test
    void givenANonExistentCustomer_whenRequestingAllOrders_thenThrowNoSuchCustomerException(){
        assertThrows(NoSuchCustomerException.class, () -> this.orderService.getAllByCustomerId("thisonedoesnormallynotexist"));
    }


    //given a client with orders, when reorder on a particular order for that client reorder (with new prices)
    @Test
    void givenAClientWithOrders_whenReorderTheOrder_thenReturnNewOrderWithNewPrice(){
        Item item = new Item("1", "bike", "2 wheels", 20, 2);
        Address address = new Address("lane",3,"b", 3000, "L-city");
        Customer customer = new Customer("az", "Alfred", "Jab", "af@jad.be", address, "044-5555-77");

        this.itemRepository.addToRepository(item);
        this.customerRepository.registerCustomerAccount(customer);

        List<ItemsToOrderDTO> items = new ArrayList<>();
        items.add(new ItemsToOrderDTO("1", 1));

        OrderDTO orderDTO = this.orderService.placeOrder(customer.getId(), items);

        item.setPrice(40);
        item.setStock(5);

        assertEquals(20, orderDTO.getAmountToPay());

        OrderDTO reOrder = this.orderService.reOrder(customer.getId(), orderDTO.getOrderId());
        assertEquals(orderDTO.getOrderedItemsList().get(0).getItem().getItemId(), reOrder.getOrderedItemsList().get(0).getItem().getItemId());
        assertEquals(orderDTO.getOrderedItemsList().get(0).getAmount(), reOrder.getOrderedItemsList().get(0).getAmount());
        assertEquals(item.getPrice(), reOrder.getOrderedItemsList().get(0).getItem().getPrice());
    }

    //given a particular client, with orders, but reorder wrong order then throw OrderNotOfC
    @Test
    void givenAClientWithOrders_butReorderOrderNotOfClient_throwException(){
        Item item = new Item("1", "bike", "2 wheels", 20, 2);
        Address address = new Address("lane",3,"b", 3000, "L-city");
        Customer customer = new Customer("az", "Alfred", "Jab", "af@jad.be", address, "044-5555-77");

        this.itemRepository.addToRepository(item);
        this.customerRepository.registerCustomerAccount(customer);

        List<ItemsToOrderDTO> items = new ArrayList<>();
        items.add(new ItemsToOrderDTO("1", 1));

        OrderDTO orderDTO = this.orderService.placeOrder(customer.getId(), items);

        assertThrows(OrderNotOfCustomerException.class, ()-> this.orderService.reOrder(customer.getId(), "thisonedoesnotexistandwillthrowanerror"));
    }

    //given a particular client, but has never ordered anything throw NoSuchC
    @Test
    void givenAClientWithNoOrders_butReorder_throwException(){
        Item item = new Item("1", "bike", "2 wheels", 20, 2);
        Address address = new Address("lane",3,"b", 3000, "L-city");
        Customer customer = new Customer("az", "Alfred", "Jab", "af@jad.be", address, "044-5555-77");

        assertThrows(NoSuchCustomerException.class, ()-> this.orderService.reOrder(customer.getId(), "thisonedoesnotexistandwillthrowanerror"));
    }




}