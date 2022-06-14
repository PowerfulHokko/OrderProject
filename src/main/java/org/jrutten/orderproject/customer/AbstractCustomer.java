package org.jrutten.orderproject.customer;

import org.jrutten.orderproject.fieldValidators.FieldValidators;

import java.util.Objects;

public abstract class AbstractCustomer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Address address;
    private final String phoneNumber;

    public AbstractCustomer(String firstName, String lastName, String email, Address address, String phoneNumber) {
        FieldValidators.guardStringNullAndBlank(firstName, lastName, email, phoneNumber);
        FieldValidators.guardObjectNull(address);
        //FieldValidators.validateEmail(email);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "AbstractCustomer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCustomer)) return false;
        AbstractCustomer that = (AbstractCustomer) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(address, that.address) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, address, phoneNumber);
    }
}
