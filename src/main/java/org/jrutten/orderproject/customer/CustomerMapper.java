package org.jrutten.orderproject.customer;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getAddress(), customer.getPhoneNumber());
    }

    public Customer toCustomer(CreateCustomerDTO createCustomerDTO) {
        return new Customer(createCustomerDTO.getFirstName(), createCustomerDTO.getLastName(), createCustomerDTO.getEmail(), createCustomerDTO.getAddress(), createCustomerDTO.getPhoneNumber());
    }
}
