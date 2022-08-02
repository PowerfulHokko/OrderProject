package org.jrutten.orderproject.customer;

import org.jrutten.orderproject.customer.representations.CreateCustomerDTO;
import org.jrutten.orderproject.customer.representations.CustomerDTO;
//import org.jrutten.orderproject.security.KeycloakGrantedAuthoritiesConverter;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = {"", "/{id}"},produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getCustomers(@PathVariable(required = false) Integer id, @RequestHeader("Authorization") String token) {
        String uToken = token;
        this.customerService.logSecurity(this.getClass(), "getCustomers", uToken);
        if(id != null) return List.of(this.customerService.getCustomerById(id));
        return this.customerService.getAllCustomers();
    }


}
