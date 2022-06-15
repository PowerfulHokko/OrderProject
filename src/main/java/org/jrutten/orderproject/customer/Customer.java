package org.jrutten.orderproject.customer;

import java.util.UUID;

public class Customer extends AbstractCustomerWithID {
    public Customer(String firstName, String lastName, String email, Address address, String phoneNumber) {
        super(firstName, lastName, email, address, phoneNumber);
    }

    public Customer(String id, String firstName, String lastName, String email, Address address, String phoneNumber) {
        super(id, firstName, lastName, email, address, phoneNumber);
    }
}
