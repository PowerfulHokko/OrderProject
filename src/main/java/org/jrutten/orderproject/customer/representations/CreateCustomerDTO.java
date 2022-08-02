package org.jrutten.orderproject.customer.representations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embedded;

@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCustomerDTO {
    String firstName;
    String lastName;
    String email;
    Address address;
}
