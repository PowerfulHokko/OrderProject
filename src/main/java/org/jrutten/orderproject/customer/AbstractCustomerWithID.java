package org.jrutten.orderproject.customer;

import java.util.UUID;

public class AbstractCustomerWithID extends AbstractCustomer {
    private String id;

    public AbstractCustomerWithID(String firstName, String lastName, String email, Address address, String phoneNumber) {
        super(firstName, lastName, email, address, phoneNumber);
        this.id = UUID.randomUUID().toString();
    }

    public AbstractCustomerWithID(String id, String firstName, String lastName, String email, Address address, String phoneNumber) {
        super(firstName, lastName, email, address, phoneNumber);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AbstractCustomerWithID{" +
                "id='" + id + '\'' + super.toString() +
                '}';
    }
}
