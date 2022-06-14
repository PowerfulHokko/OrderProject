package org.jrutten.orderproject.customer;

public class Customer extends AbstractCustomer {
    public Customer(String firstName, String lastName, String email, Address address, String phoneNumber) {
        super(firstName, lastName, email, address, phoneNumber);
    }
}
