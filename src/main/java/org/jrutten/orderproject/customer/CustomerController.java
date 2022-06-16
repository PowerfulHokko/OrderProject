package org.jrutten.orderproject.customer;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;
    private final Logger logger;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomerAccount(@RequestBody CreateCustomerDTO createCustomerDTO){
        logger.info("Post request for createCustomerAccount: " + createCustomerDTO.toString() );
        return this.customerService.createCustomerAccount(createCustomerDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = {"", "/{id}"},produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getCustomers(@PathVariable(required = false) String id){
        if(id != null && !id.isBlank()) return List.of(this.customerService.getCustomerById(id));
        return this.customerService.getAllCustomers();
    }


}
