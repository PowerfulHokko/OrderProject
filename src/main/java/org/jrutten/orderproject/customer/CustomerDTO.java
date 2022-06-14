package org.jrutten.orderproject.customer;

public class CustomerDTO extends AbstractCustomer {
    public CustomerDTO(String firstName, String lastName, String email, Address address, String phoneNumber) {
        super(firstName, lastName, email, address, phoneNumber);
    }
}
