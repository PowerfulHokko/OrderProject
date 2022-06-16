package org.jrutten.orderproject.customer;

import org.jrutten.orderproject.customer.representations.CreateCustomerDTO;
import org.jrutten.orderproject.customer.representations.Customer;
import org.jrutten.orderproject.customer.representations.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    public CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getAddress(), customer.getPhoneNumber());
    }

    public Customer toCustomer(CreateCustomerDTO createCustomerDTO) {
        return new Customer(createCustomerDTO.getFirstName(), createCustomerDTO.getLastName(), createCustomerDTO.getEmail(), createCustomerDTO.getAddress(), createCustomerDTO.getPhoneNumber());
    }

    public List<CustomerDTO> toCustomerDTO(List<Customer> allCustomers) {
        return allCustomers.stream().map(this::toCustomerDTO).collect(Collectors.toUnmodifiableList());
    }
}
