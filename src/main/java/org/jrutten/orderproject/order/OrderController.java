package org.jrutten.orderproject.order;

import org.jrutten.orderproject.order.representations.ItemsToOrderDTO;
import org.jrutten.orderproject.order.representations.OrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;
    private final Logger logger;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.logger = Logger.getLogger(this.getClass().getName());
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO placeOrder(@PathVariable String id, @RequestBody List<ItemsToOrderDTO> orderList){
        logger.info("Order request for " + id);
        OrderDTO orderDTO = this.orderService.placeOrder(id, orderList);
        return orderDTO;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderDTO> getAllOrdersFromCustomer(@PathVariable String id){
        return this.orderService.getAllByCustomerId(id);
    }


}

