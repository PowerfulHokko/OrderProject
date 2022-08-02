package org.jrutten.orderproject.customer.representations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.jrutten.orderproject.fieldValidators.FieldValidators;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {

    @Column(name = "street", nullable = false)
    String street;

    @Column(name = "street_number", nullable = false)
    int streetNumber;

    @Column(name = "bus", nullable = true)
    String bus;

    @Column(name = "postalcode", nullable = false)
    String postalCode;

    @Column(name = "city", nullable = false)
    String city;

    public Address(String street, int streetNumber, String bus, String postalCode, String city) {
        FieldValidators.guardStringNullAndBlank(street, city);
        FieldValidators.guardZeroOrLessThan(streetNumber);

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

    public String getPostalCode() {
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
