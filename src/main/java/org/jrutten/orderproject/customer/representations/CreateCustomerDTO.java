package org.jrutten.orderproject.customer.representations;

public class CreateCustomerDTO extends AbstractCustomer {
    public CreateCustomerDTO(String firstName, String lastName, String email, Address address, String phoneNumber) {
        super(firstName, lastName, email, address, phoneNumber);
    }


}
