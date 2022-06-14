package org.jrutten.orderproject.customer;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    public CustomerDTO createCustomerAccount(CreateCustomerDTO createCustomerDTO) {
        Customer customer = this.customerMapper.toCustomer(createCustomerDTO);
        CustomerDTO customerDTO = this.customerMapper.toCustomerDTO(customer);

        this.customerRepository.registerCustomerAccount(customer);

        return customerDTO;
    }
}
