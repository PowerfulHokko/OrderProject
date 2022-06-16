package org.jrutten.orderproject.customer.representations;

public class CustomerDTO extends AbstractCustomerWithID {
    public CustomerDTO(String id, String firstName, String lastName, String email, Address address, String phoneNumber) {
        super(id, firstName, lastName, email, address, phoneNumber);
    }
}
