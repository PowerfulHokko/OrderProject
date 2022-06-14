package org.jrutten.orderproject.customer;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDTO toCustomerDTO(Customer createCustomerDTO) {
        return new CustomerDTO(createCustomerDTO.getFirstName(), createCustomerDTO.getLastName(), createCustomerDTO.getEmail(), createCustomerDTO.getAddress(), createCustomerDTO.getPhoneNumber());
    }

    public Customer toCustomer(CreateCustomerDTO createCustomerDTO) {
        return new Customer(createCustomerDTO.getFirstName(), createCustomerDTO.getLastName(), createCustomerDTO.getEmail(), createCustomerDTO.getAddress(), createCustomerDTO.getPhoneNumber());
    }
}
