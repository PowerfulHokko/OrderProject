package org.jrutten.orderproject.customer.representations;

import org.jrutten.orderproject.fieldValidators.FieldValidators;

public class Address {
    private final String street;
    private final int streetNumber;
    private final String bus;
    private final int postalCode;
    private String city;

    public Address(String street, int streetNumber, String bus, int postalCode, String city) {
        FieldValidators.guardStringNullAndBlank(street, city);
        FieldValidators.guardZeroOrLessThan(streetNumber, postalCode);

        if(bus == null || bus.isBlank()){
            this.bus = "";
        } else {
            this.bus = bus;
        }

        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getBus() {
        return bus;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", bus='" + bus + '\'' +
                ", postalCode=" + postalCode +
                ", city='" + city + '\'' +
                '}';
    }
}
