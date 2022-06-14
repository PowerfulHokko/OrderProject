package org.jrutten.orderproject.customer;

public class CustomerAlreadyRegisteredException extends RuntimeException {
    public CustomerAlreadyRegisteredException(String message) {
        super(message);
    }
}
